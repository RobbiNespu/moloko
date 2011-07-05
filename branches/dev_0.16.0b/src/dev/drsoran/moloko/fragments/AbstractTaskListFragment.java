/* 
 *	Copyright (c) 2011 Ronny R�hricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.fragments;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import dev.drsoran.moloko.IConfigurable;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.MolokoPreferencesActivity;
import dev.drsoran.moloko.fragments.listeners.ITasksListListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListListener;
import dev.drsoran.moloko.prefs.TaskSortPreference;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public abstract class AbstractTaskListFragment< T extends Task > extends
         ListFragment implements LoaderCallbacks< List< T > >, IConfigurable,
         IOnSettingsChangedListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + AbstractTaskListFragment.class.getSimpleName();
   
   
   public static class Config
   {
      public final static String FILTER = "filter";
      
      public final static String TASK_SORT_ORDER = "task_sort_order";
   }
   

   protected static class OptionsMenu
   {
      public final static int SORT = R.id.menu_sort;
      
      public final static int SETTINGS = R.id.menu_settings;
      
      public final static int SYNC = R.id.menu_sync;
   }
   
   protected final static long DEFAULT_LOADER_THROTTLE_MS = 1 * DateUtils.SECOND_IN_MILLIS;
   
   private final static int TASKS_LOADER_ID = 1;
   
   private ITasksListListener listener;
   
   protected Bundle configuration;
   
   

   public abstract Intent newDefaultIntent();
   


   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof ITasksListListener )
         listener = (ITasksListListener) activity;
      else
         listener = new NullTasksListListener();
      
      MolokoApp.get( activity )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_TIMEZONE
                                                      | IOnSettingsChangedListener.RTM_DATEFORMAT
                                                      | IOnSettingsChangedListener.RTM_TIMEFORMAT,
                                                   this );
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
      
      MolokoApp.get( getActivity() ).unregisterOnSettingsChangedListener( this );
   }
   


   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      configure( getArguments() );
   }
   


   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      
      setHasOptionsMenu( true );
      
      getLoaderManager().initLoader( TASKS_LOADER_ID, configuration, this );
   }
   


   @Override
   public void setArguments( Bundle args )
   {
      super.setArguments( args );
      configure( args );
   }
   


   protected CharSequence getEmptyListText()
   {
      return getString( R.string.abstaskslist_no_tasks );
   }
   


   @Override
   public void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   public Bundle getConfiguration()
   {
      return new Bundle( configuration );
   }
   


   public void configure( Bundle config )
   {
      if ( configuration == null )
         configuration = createDefaultConfiguration();
      
      if ( config != null )
      {
         if ( config.containsKey( Config.FILTER ) )
            configuration.putParcelable( Config.FILTER,
                                         config.getParcelable( Config.FILTER ) );
         configuration.putInt( Config.TASK_SORT_ORDER,
                               config.getInt( Config.TASK_SORT_ORDER,
                                              configuration.getInt( Config.TASK_SORT_ORDER ) ) );
      }
   }
   


   public Bundle createDefaultConfiguration()
   {
      final Bundle bundle = new Bundle();
      bundle.putParcelable( Config.FILTER,
                            new RtmSmartFilter( Strings.EMPTY_STRING ) );
      bundle.putInt( Config.TASK_SORT_ORDER, getDefaultTaskSort() );
      
      return bundle;
   }
   


   public void onSettingsChanged( int which,
                                  HashMap< Integer, Object > oldValues )
   {
      if ( which == IOnSettingsChangedListener.RTM_DATEFORMAT
         || which == IOnSettingsChangedListener.RTM_TIMEZONE
         || which == IOnSettingsChangedListener.RTM_TIMEFORMAT )
      {
         notifyDataSetChanged();
      }
   }
   


   @Override
   public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
   {
      menu.add( Menu.NONE,
                OptionsMenu.SETTINGS,
                Menu.CATEGORY_SECONDARY,
                R.string.phr_settings ).setIcon( R.drawable.ic_menu_settings );
      
      UIUtils.addOptionsMenuIntent( getActivity(),
                                    menu,
                                    OptionsMenu.SETTINGS,
                                    MolokoPreferencesActivity.class );
   }
   


   @Override
   public void onPrepareOptionsMenu( Menu menu )
   {
      UIUtils.addSyncMenuItem( getActivity(),
                               menu,
                               OptionsMenu.SYNC,
                               Menu.CATEGORY_SECONDARY );
      
      UIUtils.addOptionalMenuItem( menu,
                                   OptionsMenu.SORT,
                                   getString( R.string.abstaskslist_menu_opt_sort ),
                                   Menu.CATEGORY_CONTAINER,
                                   R.drawable.ic_menu_sort,
                                   hasMultipleTasks() );
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.SORT:
            showChooseTaskSortDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   protected void showChooseTaskSortDialog()
   {
      final Context context = getActivity();
      new AlertDialog.Builder( context ).setIcon( R.drawable.ic_dialog_sort )
                                        .setTitle( R.string.abstaskslist_dlg_sort_title )
                                        .setSingleChoiceItems( R.array.app_sort_options,
                                                               getTaskSortIndex( getTaskSortConfiguration() ),
                                                               defaultChooseTaskSortDialogListener )
                                        .setNegativeButton( R.string.btn_cancel,
                                                            defaultChooseTaskSortDialogListener )
                                        .show();
   }
   


   @Override
   public void onListItemClick( ListView l, View v, int position, long id )
   {
      listener.onOpenTask( position );
   }
   


   public IFilter getFilter()
   {
      return configuration.getParcelable( Config.FILTER );
   }
   


   public RtmSmartFilter getRtmSmartFilter()
   {
      final IFilter filter = getFilter();
      
      return ( filter instanceof RtmSmartFilter ) ? (RtmSmartFilter) filter
                                                 : null;
   }
   


   public boolean hasTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 0;
   }
   


   public boolean hasMultipleTasks()
   {
      return getListAdapter() != null && getListAdapter().getCount() > 1;
   }
   


   @SuppressWarnings( "unchecked" )
   public T getTask( int pos )
   {
      return (T) getListAdapter().getItem( pos );
   }
   


   public int getTaskPos( View view )
   {
      return getListView().getPositionForView( view );
   }
   


   public T getTask( View view )
   {
      return getTask( getTaskPos( view ) );
   }
   


   public boolean hasRtmWriteAccess()
   {
      return !AccountUtils.isReadOnlyAccess( getActivity() );
   }
   


   public void reload()
   {
      getLoaderManager().restartLoader( TASKS_LOADER_ID,
                                        getConfiguration(),
                                        this );
   }
   


   public int getTaskSortConfiguration()
   {
      return configuration.getInt( Config.TASK_SORT_ORDER );
   }
   


   public boolean shouldResortTasks( int taskSort )
   {
      return getTaskSortConfiguration() != taskSort;
   }
   


   protected abstract int getDefaultTaskSort();
   


   protected String resolveTaskSortToSqlite( int taskSort )
   {
      return Queries.resolveTaskSortToSqlite( taskSort );
   }
   


   public int getTaskSortValue( int idx )
   {
      return TaskSortPreference.getValueOfIndex( idx );
   }
   


   public int getTaskSortIndex( int value )
   {
      return TaskSortPreference.getIndexOfValue( value );
   }
   


   public void showError( CharSequence text )
   {
      setEmptyText( text );
      getLoaderManager().destroyLoader( TASKS_LOADER_ID );
   }
   


   protected abstract ListAdapter createEmptyListAdapter();
   


   protected abstract ListAdapter createListAdapterForResult( List< T > result,
                                                              IFilter filter );
   


   protected abstract void notifyDataSetChanged();
   


   public void onLoadFinished( Loader< List< T >> loader, List< T > data )
   {
      showLoadingSpinner( false );
      setListAdapter( createListAdapterForResult( data, getFilter() ) );
   }
   


   protected void showLoadingSpinner( boolean show )
   {
      getActivity().findViewById( android.R.id.empty )
                   .setVisibility( show ? View.GONE : View.VISIBLE );
      getActivity().findViewById( R.id.loading_spinner )
                   .setVisibility( show ? View.VISIBLE : View.GONE );
   }
   


   public void onLoaderReset( Loader< List< T >> loader )
   {
      setListAdapter( createEmptyListAdapter() );
   }
   
   protected final DialogInterface.OnClickListener defaultChooseTaskSortDialogListener = new OnClickListener()
   {
      public void onClick( DialogInterface dialog, int which )
      {
         switch ( which )
         {
            case Dialog.BUTTON_NEGATIVE:
               break;
            
            default :
               final int newTaskSort = getTaskSortValue( which );
               
               dialog.dismiss();
               
               if ( newTaskSort != -1 && shouldResortTasks( newTaskSort ) )
                  listener.onTaskSortChanged( newTaskSort );
               
               break;
         }
      }
   };
}
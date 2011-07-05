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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.TextView;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.adapters.FullDetailedTasksListFragmentAdapter;
import dev.drsoran.moloko.dialogs.MultiChoiceDialog;
import dev.drsoran.moloko.fragments.listeners.IFullDetailedTasksListListener;
import dev.drsoran.moloko.fragments.listeners.NullTasksListListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.loaders.ListTasksLoader;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterParsing;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.RtmSmartFilter;


public class FullDetailedTasksListFragment extends
         AbstractTaskListFragment< ListTask > implements View.OnClickListener,
         IOnSettingsChangedListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + FullDetailedTasksListFragment.class.getSimpleName();
   
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_VIEW,
                                           "vnd.android.cursor.dir/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   protected static class OptionsMenu extends
            AbstractTaskListFragment.OptionsMenu
   {
      public final static int EDIT_MULTIPLE_TASKS = R.id.menu_edit_multiple_tasks;
   }
   

   private final static class CtxtMenu
   {
      public final static int OPEN_TASK = R.id.ctx_menu_open_task;
      
      public final static int EDIT_TASK = R.id.ctx_menu_edit_task;
      
      public final static int COMPLETE_TASK = R.id.ctx_menu_complete_task;
      
      public final static int POSTPONE_TASK = R.id.ctx_menu_postpone_task;
      
      public final static int DELETE_TASK = R.id.ctx_menu_delete_task;
      
      public final static int OPEN_LIST = R.id.ctx_menu_open_list;
      
      public final static int TAG = R.id.ctx_menu_open_tag;
      
      public final static int TAGS = R.id.ctx_menu_open_tags;
      
      public final static int TASKS_AT_LOCATION = R.id.ctx_menu_open_tasks_at_loc;
      
      public final static int NOTES = R.id.ctx_menu_open_notes;
   }
   
   private IFullDetailedTasksListListener listener;
   
   

   public static FullDetailedTasksListFragment newInstance( Bundle configuration )
   {
      final FullDetailedTasksListFragment fragment = new FullDetailedTasksListFragment();
      
      fragment.setArguments( configuration );
      
      return fragment;
   }
   


   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   


   @Override
   public Intent newDefaultIntent()
   {
      return new Intent( INTENT_FILTER.getAction( 0 ), Tasks.CONTENT_URI );
   }
   


   @Override
   public void onAttach( Activity activity )
   {
      super.onAttach( activity );
      
      if ( activity instanceof IFullDetailedTasksListListener )
         listener = (IFullDetailedTasksListListener) activity;
      else
         listener = new NullTasksListListener();
   }
   


   @Override
   public void onDetach()
   {
      super.onDetach();
      listener = null;
   }
   


   @Override
   public void onActivityCreated( Bundle savedInstanceState )
   {
      super.onActivityCreated( savedInstanceState );
      
      registerForContextMenu( getListView() );
   }
   


   @Override
   public View onCreateView( LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState )
   {
      return inflater.inflate( R.layout.taskslist_fragment, container, false );
   }
   


   @Override
   public void onPrepareOptionsMenu( Menu menu )
   {
      super.onPrepareOptionsMenu( menu );
      
      UIUtils.addOptionalMenuItem( menu,
                                   OptionsMenu.EDIT_MULTIPLE_TASKS,
                                   getString( R.string.abstaskslist_menu_opt_edit_multiple ),
                                   Menu.CATEGORY_CONTAINER,
                                   R.drawable.ic_menu_edit_multiple_tasks,
                                   hasMultipleTasks() && hasRtmWriteAccess() );
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.EDIT_MULTIPLE_TASKS:
            listener.onSelectTasks();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   @Override
   public void onCreateContextMenu( ContextMenu menu,
                                    View v,
                                    ContextMenuInfo menuInfo )
   {
      super.onCreateContextMenu( menu, v, menuInfo );
      
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
      
      final ListTask task = getTask( info.position );
      
      menu.add( Menu.NONE,
                CtxtMenu.OPEN_TASK,
                Menu.NONE,
                getString( R.string.phr_open_with_name, task.getName() ) );
      
      if ( hasRtmWriteAccess() )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.EDIT_TASK,
                   Menu.NONE,
                   getString( R.string.phr_edit_with_name, task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.COMPLETE_TASK,
                   Menu.NONE,
                   getString( task.getCompleted() == null
                                                         ? R.string.abstaskslist_listitem_ctx_complete_task
                                                         : R.string.abstaskslist_listitem_ctx_uncomplete_task,
                              task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.POSTPONE_TASK,
                   Menu.NONE,
                   getString( R.string.abstaskslist_listitem_ctx_postpone_task,
                              task.getName() ) );
         menu.add( Menu.NONE,
                   CtxtMenu.DELETE_TASK,
                   Menu.NONE,
                   getString( R.string.phr_delete_with_name, task.getName() ) );
      }
      
      final RtmSmartFilter filter = getRtmSmartFilter();
      
      // If the list name was in the filter then we are in one list only. So no need to
      // open it again.
      if ( filter == null
         || !RtmSmartFilterParsing.hasOperatorAndValue( filter.getTokens(),
                                                        RtmSmartFilterLexer.OP_LIST,
                                                        task.getListName(),
                                                        false ) )
      {
         menu.add( Menu.NONE,
                   CtxtMenu.OPEN_LIST,
                   Menu.NONE,
                   getString( R.string.abstaskslist_listitem_ctx_open_list,
                              task.getListName() ) );
      }
      
      final int tagsCount = task.getTags().size();
      if ( tagsCount > 0 )
      {
         final View tagsContainer = info.targetView.findViewById( R.id.taskslist_listitem_additionals_container );
         
         if ( tagsContainer.getVisibility() == View.VISIBLE )
            menu.add( Menu.NONE,
                      tagsCount == 1 ? CtxtMenu.TAG : CtxtMenu.TAGS,
                      Menu.NONE,
                      getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                        tagsCount,
                                                        task.getTags().get( 0 ) ) );
      }
      
      final String locationName = task.getLocationName();
      if ( !TextUtils.isEmpty( locationName ) )
      {
         final View locationView = info.targetView.findViewById( R.id.taskslist_listitem_location );
         
         if ( locationView.getVisibility() == View.VISIBLE )
         {
            menu.add( Menu.NONE,
                      CtxtMenu.TASKS_AT_LOCATION,
                      Menu.NONE,
                      getString( R.string.abstaskslist_listitem_ctx_tasks_at_location,
                                 locationName ) );
         }
      }
      
      final int notesCount = task.getNumberOfNotes();
      if ( notesCount > 0 )
         menu.add( Menu.NONE,
                   CtxtMenu.NOTES,
                   Menu.NONE,
                   getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_notes,
                                                     notesCount ) );
   }
   


   @Override
   public boolean onContextItemSelected( MenuItem item )
   {
      final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
      
      switch ( item.getItemId() )
      {
         case CtxtMenu.OPEN_TASK:
            listener.onOpenTask( info.position );
            return true;
            
         case CtxtMenu.EDIT_TASK:
            listener.onEditTask( info.position );
            return true;
            
         case CtxtMenu.COMPLETE_TASK:
            onCompleteTask( info.position );
            return true;
            
         case CtxtMenu.POSTPONE_TASK:
            onPostponeTask( info.position );
            return true;
            
         case CtxtMenu.DELETE_TASK:
            onDeleteTask( info.position );
            return true;
            
         case CtxtMenu.OPEN_LIST:
            listener.onOpenList( info.position,
                                 getTask( info.position ).getListId() );
            return true;
            
         case CtxtMenu.TAG:
            showTasksWithTag( getTask( info.position ).getTags().get( 0 ) );
            return true;
            
         case CtxtMenu.TAGS:
            showChooseTagsDialog( getTask( info.position ).getTags() );
            return true;
            
         case CtxtMenu.TASKS_AT_LOCATION:
            listener.onOpenLocation( info.position,
                                     getTask( info.position ).getLocationId() );
            return true;
            
         case CtxtMenu.NOTES:
            listener.onOpenNotes( info.position,
                                  getTask( info.position ).getNotes( getActivity() ) );
            return true;
            
         default :
            return super.onContextItemSelected( item );
      }
   }
   


   public void onClick( View view )
   {
      switch ( view.getId() )
      {
         case R.id.tags_layout_btn_tag:
            final String tag = ( (TextView) view ).getText().toString();
            showTasksWithTag( tag );
            break;
         
         case R.id.taskslist_listitem_btn_list_name:
            listener.onOpenList( getTaskPos( view ),
                                 getTask( view ).getListId() );
            break;
         
         case R.id.taskslist_listitem_location:
            listener.onOpenLocation( getTaskPos( view ),
                                     getTask( view ).getLocationId() );
            break;
         
         default :
            break;
      }
   }
   


   private void showTasksWithTag( String tag )
   {
      listener.onShowTasksWithTag( tag );
   }
   


   protected void showChooseTagsDialog( List< String > tags )
   {
      final List< CharSequence > tmp = new ArrayList< CharSequence >( tags.size() );
      
      for ( String tag : tags )
         tmp.add( tag );
      
      new MultiChoiceDialog( getActivity(),
                             tmp,
                             chooseMultipleTagsDialogListener ).setTitle( getResources().getQuantityString( R.plurals.taskslist_listitem_ctx_tags,
                                                                                                            tags.size() ) )
                                                               .setIcon( R.drawable.ic_dialog_tag )
                                                               .setButtonText( getString( R.string.abstaskslist_dlg_show_tags_and ) )
                                                               .setButton2Text( getString( R.string.abstaskslist_dlg_show_tags_or ) )
                                                               .show();
   }
   


   @Override
   protected int getDefaultTaskSort()
   {
      return MolokoApp.getSettings().getTaskSort();
   }
   


   private void onCompleteTask( int pos )
   {
      final ListTask task = getTask( pos );
      TaskEditUtils.setTaskCompletion( getActivity(),
                                       task,
                                       task.getCompleted() == null );
   }
   


   private void onPostponeTask( int pos )
   {
      TaskEditUtils.postponeTask( getActivity(), getTask( pos ) );
   }
   


   private void onDeleteTask( int pos )
   {
      final ListTask task = getTask( pos );
      final Activity activity = getActivity();
      
      UIUtils.newDeleteElementDialog( activity, task.getName(), new Runnable()
      {
         public void run()
         {
            TaskEditUtils.deleteTask( activity, task );
         }
      }, null ).show();
   }
   


   public Loader< List< ListTask >> onCreateLoader( int id, Bundle config )
   {
      showLoadingSpinner( true );
      
      final IFilter filter = config.getParcelable( Config.FILTER );
      final String selection = filter != null ? filter.getSqlSelection() : null;
      final String order = resolveTaskSortToSqlite( config.getInt( Config.TASK_SORT_ORDER ) );
      
      final ListTasksLoader loader = new ListTasksLoader( getActivity(),
                                                          selection,
                                                          order );
      loader.setUpdateThrottle( DEFAULT_LOADER_THROTTLE_MS );
      
      return loader;
   }
   


   @Override
   protected ListAdapter createEmptyListAdapter()
   {
      return new FullDetailedTasksListFragmentAdapter( getActivity(),
                                                       R.layout.fulldetailed_taskslist_listitem );
   }
   


   @Override
   protected ListAdapter createListAdapterForResult( List< ListTask > result,
                                                     IFilter filter )
   {
      final int flags = 0;
      return new FullDetailedTasksListFragmentAdapter( getActivity(),
                                                       R.layout.fulldetailed_taskslist_listitem,
                                                       result,
                                                       filter,
                                                       flags,
                                                       this );
   }
   


   @Override
   public FullDetailedTasksListFragmentAdapter getListAdapter()
   {
      return (FullDetailedTasksListFragmentAdapter) super.getListAdapter();
   }
   


   @Override
   protected void notifyDataSetChanged()
   {
      if ( getListAdapter() != null )
         getListAdapter().notifyDataSetChanged();
   }
   
   private final DialogInterface.OnClickListener chooseMultipleTagsDialogListener = new OnClickListener()
   {
      public void onClick( DialogInterface dialog, int which )
      {
         final boolean andLink = ( which == DialogInterface.BUTTON1 );
         final List< String > chosenTags = new LinkedList< String >();
         
         {
            final MultiChoiceDialog tagsChoice = (MultiChoiceDialog) dialog;
            final CharSequence[] tags = tagsChoice.getItems();
            final boolean[] states = tagsChoice.getStates();
            
            // filter out the chosen tags
            for ( int i = 0; i < states.length; i++ )
               if ( states[ i ] )
                  chosenTags.add( tags[ i ].toString() );
         }
         
         listener.onShowTasksWithTags( chosenTags,
                                       andLink ? RtmSmartFilterLexer.AND_LIT
                                              : RtmSmartFilterLexer.OR_LIT );
      }
   };
}
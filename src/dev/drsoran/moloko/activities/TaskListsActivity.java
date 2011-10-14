/*
 * Copyright (c) 2010 Ronny R�hricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.AbstractTasksListActivity.Config;
import dev.drsoran.moloko.fragments.AbstractTasksListFragment;
import dev.drsoran.moloko.fragments.TaskListsFragment;
import dev.drsoran.moloko.fragments.dialogs.AddRenameListDialogFragment;
import dev.drsoran.moloko.fragments.listeners.ITaskListsFragmentListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmListWithTaskCount;
import dev.drsoran.rtm.RtmSmartFilter;


public class TaskListsActivity extends MolokoFragmentActivity implements
         ITaskListsFragmentListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + TaskListsActivity.class.getSimpleName();
   
   
   protected static class OptionsMenu
   {
      public final static int ADD_LIST = R.id.menu_add_list;
   }
   
   

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.tasklists_activity );
   }
   


   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      UIUtils.addSettingsMenuItem( this,
                                   menu,
                                   MenuCategory.ALTERNATIVE,
                                   MenuItem.SHOW_AS_ACTION_NEVER );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.ADD_LIST,
                                   getString( R.string.tasklists_menu_add_list ),
                                   MenuCategory.CONTAINER,
                                   Menu.NONE,
                                   R.drawable.ic_menu_add_list,
                                   MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                   AccountUtils.isWriteableAccess( this ) );
      
      UIUtils.addSearchMenuItem( this,
                                 menu,
                                 MenuCategory.ALTERNATIVE,
                                 MenuItem.SHOW_AS_ACTION_IF_ROOM );
      
      UIUtils.addSyncMenuItem( this,
                               menu,
                               MenuCategory.ALTERNATIVE,
                               MenuItem.SHOW_AS_ACTION_IF_ROOM );
      return true;
   }
   


   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.ADD_LIST:
            showAddListDialog();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   


   @Override
   public void openList( int pos )
   {
      final RtmListWithTaskCount rtmList = getRtmList( pos );
      
      // Check if the smart filter could be parsed. Otherwise
      // we do not fire the intent.
      if ( rtmList.isSmartFilterValid() )
      {
         final String listName = rtmList.getName();
         
         final Intent intent = new Intent( Intent.ACTION_VIEW,
                                           Tasks.CONTENT_URI );
         
         intent.putExtra( Config.TITLE, getString( R.string.taskslist_titlebar,
                                                   listName ) );
         
         RtmSmartFilter filter = rtmList.getSmartFilter();
         
         // If we have no smart filter we use the list name as "list:" filter
         if ( filter == null )
         {
            filter = new RtmSmartFilter( RtmSmartFilterLexer.OP_LIST_LIT
               + RtmSmartFilterLexer.quotify( listName ) );
         }
         
         intent.putExtra( Lists.LIST_NAME, rtmList.getName() );
         intent.putExtra( AbstractTasksListFragment.Config.FILTER, filter );
         
         startActivity( intent );
      }
   }
   


   @Override
   public void openChild( Intent intent )
   {
      startActivity( intent );
   }
   


   @Override
   public void renameList( int pos )
   {
      showRenameListDialog( getRtmList( pos ) );
   }
   


   private void showRenameListDialog( RtmListWithTaskCount list )
   {
      createAddRenameListDialogFragment( createRenameListFragmentConfig( list ) );
   }
   


   private Bundle createRenameListFragmentConfig( RtmListWithTaskCount list )
   {
      final Bundle config = new Bundle();
      
      config.putParcelable( AddRenameListDialogFragment.Config.LIST,
                            list.getRtmList() );
      if ( list.getRtmList().getSmartFilter() != null )
         config.putParcelable( AddRenameListDialogFragment.Config.FILTER,
                               list.getRtmList().getSmartFilter() );
      
      return config;
   }
   


   private void showAddListDialog()
   {
      createAddRenameListDialogFragment( Bundle.EMPTY );
   }
   


   private RtmListWithTaskCount getRtmList( int pos )
   {
      final TaskListsFragment taskListsFragment = (TaskListsFragment) getSupportFragmentManager().findFragmentById( R.id.frag_tasklists );
      return taskListsFragment.getRtmList( pos );
   }
   


   private void createAddRenameListDialogFragment( Bundle config )
   {
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      dialogFragment.show( transaction,
                           String.valueOf( R.id.frag_add_rename_list ) );
   }
   


   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_tasklists };
   }
   
}

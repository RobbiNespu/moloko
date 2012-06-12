/*
 * Copyright (c) 2012 Ronny R�hricht
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

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IFilter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.actionmodes.QuickAddTaskActionModeCallback;
import dev.drsoran.moloko.actionmodes.listener.IQuickAddTaskActionModeListener;
import dev.drsoran.moloko.actionmodes.listener.ITasksListActionModeListener;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.fragments.FullDetailedTasksListFragment;
import dev.drsoran.moloko.fragments.dialogs.AddRenameListDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChooseTagsDialogFragment;
import dev.drsoran.moloko.fragments.listeners.IShowTasksWithTagsListener;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmSmartFilter;
import dev.drsoran.rtm.Task;


public abstract class AbstractFullDetailedTasksListActivity extends
         AbstractTasksListActivity implements ITasksListActionModeListener,
         IShowTasksWithTagsListener, IQuickAddTaskActionModeListener
{
   @InstanceState( key = "ACTIONMODE_QUICK_ADD_TASK" )
   private boolean quickAddTaskActionModeActive;
   
   private ActionMode activeActionMode;
   
   
   
   protected AbstractFullDetailedTasksListActivity()
   {
      registerAnnotatedConfiguredInstance( this,
                                           AbstractFullDetailedTasksListActivity.class );
   }
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      if ( quickAddTaskActionModeActive )
      {
         showQuickAddTaskInput();
      }
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case R.id.menu_quick_add_task:
            showQuickAddTaskInput();
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   @Override
   public void onActionModeStarted( ActionMode mode )
   {
      activeActionMode = mode;
      super.onActionModeStarted( mode );
   }
   
   
   
   @Override
   public void onActionModeFinished( ActionMode mode )
   {
      activeActionMode = null;
      quickAddTaskActionModeActive = false;
      super.onActionModeFinished( mode );
   }
   
   
   
   @Override
   public void onQuickAddAddNewTask( Bundle parsedValues )
   {
      activeActionMode.finish();
      startActivity( Intents.createAddTaskIntent( this, parsedValues ) );
   }
   
   
   
   @Override
   public void onOpenTask( int pos )
   {
      startActivity( Intents.createOpenTaskIntent( this, getTask( pos ).getId() ) );
   }
   
   
   
   @Override
   public final void onShowTasksWithTags( List< String > tags )
   {
      if ( tags.size() == 1 )
      {
         onOpenChoosenTags( tags, null );
      }
      else if ( tags.size() > 1 )
      {
         showChooseTagsDialog( tags );
      }
   }
   
   
   
   /*
    * Callback from ChooseTagsDialogFragment after choosing tags and a logical operation on them.
    */
   @Override
   public final void onShowTasksWithTags( List< String > tags,
                                          LogicalOperation operation )
   {
      final String logOpString = determineLogicalOperationString( operation );
      onOpenChoosenTags( tags, logOpString );
   }
   
   
   
   @Override
   public void onOpenTaskLocation( Task task )
   {
      startActivityPreserveHomeAction( Intents.createOpenLocationIntentByName( this,
                                                                               task.getLocationName() ) );
   }
   
   
   
   @Override
   public void onEditTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         startActivity( Intents.createEditTaskIntent( this, tasks.get( 0 ) ) );
      }
      else
      {
         startActivity( Intents.createEditMultipleTasksIntent( this, tasks ) );
      }
   }
   
   
   
   @Override
   public void onCompleteTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                                  tasks,
                                                                                  true );
         applyModifications( modifications );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_complete,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_complete ).setMessage( message )
                                                                                 .setPositiveButton( R.string.btn_complete )
                                                                                 .setNegativeButton( R.string.btn_cancel )
                                                                                 .show( this );
      }
   }
   
   
   
   @Override
   public void onIncompleteTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                                  tasks,
                                                                                  false );
         applyModifications( modifications );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_incomplete,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_incomplete ).setMessage( message )
                                                                                   .setPositiveButton( R.string.btn_uncomplete )
                                                                                   .setNegativeButton( R.string.btn_cancel )
                                                                                   .show( this );
      }
   }
   
   
   
   @Override
   public void onPostponeTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         final ApplyChangesInfo modifications = TaskEditUtils.postponeTasks( this,
                                                                             tasks );
         applyModifications( modifications );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_postpone,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_postpone ).setMessage( message )
                                                                                 .setPositiveButton( R.string.btn_postpone )
                                                                                 .setNegativeButton( R.string.btn_cancel )
                                                                                 .show( this );
      }
   }
   
   
   
   @Override
   public void onDeleteTasks( List< ? extends Task > tasks )
   {
      if ( tasks.size() == 1 )
      {
         final Task task = tasks.get( 0 );
         UIUtils.showDeleteElementDialog( this, task.getName(), task.getId() );
      }
      else
      {
         final String message = getResources().getQuantityString( R.plurals.tasks_delete,
                                                                  tasks.size(),
                                                                  tasks.size() );
         new AlertDialogFragment.Builder( R.id.dlg_selectmultipletasks_delete ).setMessage( message )
                                                                               .setPositiveButton( R.string.btn_delete )
                                                                               .setNegativeButton( R.string.btn_cancel )
                                                                               .show( this );
      }
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      switch ( dialogId )
      {
      
         case R.id.dlg_selectmultipletasks_complete:
            if ( which == Dialog.BUTTON_POSITIVE )
               completeSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_incomplete:
            if ( which == Dialog.BUTTON_POSITIVE )
               incompleteSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_postpone:
            if ( which == Dialog.BUTTON_POSITIVE )
               postponeSelectedTasks( getSelectedTasks() );
            break;
         
         case R.id.dlg_selectmultipletasks_delete:
            if ( which == Dialog.BUTTON_POSITIVE )
               deleteSelectedTasks( getSelectedTasks() );
            break;
         
         default :
            super.onAlertDialogFragmentClick( dialogId, tag, which );
            break;
      }
   }
   
   
   
   protected void onOpenChoosenTags( List< String > tags,
                                     String logicalOperation )
   {
      startActivityPreserveHomeAction( Intents.createOpenTagsIntent( this,
                                                                     tags,
                                                                     logicalOperation ) );
   }
   
   
   
   private void completeSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                               tasks,
                                                                               true );
      applyModifications( modifications );
      getTasksListFragment().getListView().clearChoices();
   }
   
   
   
   private void incompleteSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.setTasksCompletion( this,
                                                                               tasks,
                                                                               false );
      applyModifications( modifications );
      getTasksListFragment().getListView().clearChoices();
   }
   
   
   
   private void postponeSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.postponeTasks( this,
                                                                          tasks );
      applyModifications( modifications );
      getTasksListFragment().getListView().clearChoices();
   }
   
   
   
   private void deleteSelectedTasks( List< ? extends Task > tasks )
   {
      final ApplyChangesInfo modifications = TaskEditUtils.deleteTasks( this,
                                                                        tasks );
      applyModifications( modifications );
      getTasksListFragment().getListView().clearChoices();
   }
   
   
   
   private void showQuickAddTaskInput()
   {
      if ( activeActionMode != null )
      {
         throw new IllegalStateException( "ActionMode already started." );
      }
      
      IFilter filter = getConfiguredFilter();
      if ( !( filter instanceof RtmSmartFilter ) )
      {
         filter = null;
      }
      
      startActionMode( new QuickAddTaskActionModeCallback( this,
                                                           (RtmSmartFilter) filter ) );
      quickAddTaskActionModeActive = true;
   }
   
   
   
   private static String determineLogicalOperationString( LogicalOperation operation )
   {
      final String logOpString;
      
      switch ( operation )
      {
         case AND:
            logOpString = RtmSmartFilterLexer.AND_LIT;
            break;
         
         case OR:
            logOpString = RtmSmartFilterLexer.OR_LIT;
            break;
         
         default :
            logOpString = null;
            break;
      }
      return logOpString;
   }
   
   
   
   protected void showAddListDialog()
   {
      final Bundle config = new Bundle();
      config.putParcelable( AddRenameListDialogFragment.Config.FILTER,
                            getConfiguredFilter() );
      
      final DialogFragment dialogFragment = AddRenameListDialogFragment.newInstance( config );
      UIUtils.showDialogFragment( this,
                                  dialogFragment,
                                  String.valueOf( R.id.frag_add_rename_list ) );
   }
   
   
   
   private void showChooseTagsDialog( List< String > tags )
   {
      ChooseTagsDialogFragment.show( this, tags );
   }
   
   
   
   private FullDetailedTasksListFragment getTasksListFragment()
   {
      final FullDetailedTasksListFragment fragment = (FullDetailedTasksListFragment) findAddedFragmentById( R.id.frag_taskslist );
      return fragment;
   }
   
   
   
   private List< Task > getSelectedTasks()
   {
      return getTasksListFragment().getMolokoListView().getCheckedItems();
   }
}

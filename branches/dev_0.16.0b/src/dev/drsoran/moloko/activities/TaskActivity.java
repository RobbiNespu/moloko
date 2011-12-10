/*
 * Copyright (c) 2011 Ronny R�hricht
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

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;
import com.mdt.rtm.data.RtmTaskNote;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.fragments.AbstractTaskEditFragment;
import dev.drsoran.moloko.fragments.NoteAddFragment;
import dev.drsoran.moloko.fragments.NoteEditFragment;
import dev.drsoran.moloko.fragments.NoteFragment;
import dev.drsoran.moloko.fragments.TaskEditFragment;
import dev.drsoran.moloko.fragments.TaskFragment;
import dev.drsoran.moloko.fragments.base.AbstractPickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.AlertDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.ChangeTagsDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.DuePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.EstimatePickerDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.LocationChooserDialogFragment;
import dev.drsoran.moloko.fragments.dialogs.RecurrPickerDialogFragment;
import dev.drsoran.moloko.fragments.factories.TaskFragmentFactory;
import dev.drsoran.moloko.fragments.listeners.IChangeTagsFragmentListener;
import dev.drsoran.moloko.fragments.listeners.ILoaderFragmentListener;
import dev.drsoran.moloko.fragments.listeners.IPickerDialogListener;
import dev.drsoran.moloko.fragments.listeners.ITaskEditFragmentListener;
import dev.drsoran.moloko.fragments.listeners.ITaskFragmentListener;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.NoteEditUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.TaskEditUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.Task;


public class TaskActivity extends MolokoEditFragmentActivity implements
         ITaskFragmentListener, ITaskEditFragmentListener,
         ILoaderFragmentListener, IChangeTagsFragmentListener,
         IPickerDialogListener
{
   public static class Config
   {
      public final static String TASK = "task";
      
      private final static String EDIT_MODE_FRAG_ID = "editModeFragmentId";
      
      private final static String NOTE_FRAGMENT_CONTAINERS = "note_fragment_containers";
      
      private final static String NOTE_ID_TO_DELETE = "note_id_to_delete";
   }
   
   
   protected static class OptionsMenu
   {
      public final static int POSTPONE_TASK = R.id.menu_postpone_selected_tasks;
      
      public final static int COMPLETE_TASK = R.id.menu_complete_selected_tasks;
      
      public final static int UNCOMPLETE_TASK = R.id.menu_uncomplete_selected_tasks;
      
      public final static int DELETE_TASK = R.id.menu_delete_selected_tasks;
      
      public final static int SAVE = R.id.menu_save;
      
      public final static int ABORT = R.id.menu_abort_edit;
   }
   
   
   private enum FinishEditMode
   {
      SAVE, CANCELED, FORCE_CANCELED
   }
   
   
   private final static class NoteFragmentContainerState implements Parcelable
   {
      @SuppressWarnings( "unused" )
      public static final Parcelable.Creator< NoteFragmentContainerState > CREATOR = new Parcelable.Creator< NoteFragmentContainerState >()
      {
         
         @Override
         public NoteFragmentContainerState createFromParcel( Parcel source )
         {
            return new NoteFragmentContainerState( source );
         }
         
         
         
         @Override
         public NoteFragmentContainerState[] newArray( int size )
         {
            return new NoteFragmentContainerState[ size ];
         }
         
      };
      
      public final String noteId;
      
      public final int noteFragmentContainerId;
      
      
      
      public NoteFragmentContainerState( String noteId,
         int noteFragmentContainerId )
      {
         this.noteId = noteId;
         this.noteFragmentContainerId = noteFragmentContainerId;
      }
      
      
      
      public NoteFragmentContainerState( Parcel source )
      {
         noteId = source.readString();
         noteFragmentContainerId = source.readInt();
      }
      
      
      
      @Override
      public void writeToParcel( Parcel dest, int flags )
      {
         dest.writeString( noteId );
         dest.writeInt( noteFragmentContainerId );
      }
      
      
      
      @Override
      public int describeContents()
      {
         return 0;
      }
   }
   
   private final static String TASK_NOTE_LAYOUT_TAG_STUB = "frag_layout_";
   
   private final static int NEW_NOTE_TEMPORARY_CONTAINER_ID = 1;
   
   private final static String NEW_NOTE_TEMPORARY_ID = Integer.toString( NEW_NOTE_TEMPORARY_CONTAINER_ID );
   
   private final static String DELETE_TASK_DIALOG_TAG = "del_task?";
   
   private final static String DELETE_NOTE_DIALOG_TAG = "del_note?";
   
   private final static String FINISH_EDIT_W_CHANGES_END_EDITING = "finish_w_changes_end_edit";
   
   private final static String FINISH_EDIT_W_CHANGES_FINISH_ACTIVITY = "finish_w_changes_finish_activity";
   
   
   
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.task_activity );
      
      createTaskFragment();
      restoreNoteFragmentContainers();
      
      onReEvaluateRtmAccessLevel( AccountUtils.getAccessLevel( this ) );
      
      setActivityInEditMode( getConfiguredEditModeFragmentId() );
   }
   
   
   
   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      saveNoteFragmentContainers();
      
      super.onSaveInstanceState( outState );
   }
   
   
   
   private void saveNoteFragmentContainers()
   {
      final Task task = getTask();
      
      if ( task != null )
      {
         final List< NoteFragmentContainerState > noteFragmentContainers = new ArrayList< NoteFragmentContainerState >( task.getNumberOfNotes() );
         
         if ( task.getNumberOfNotes() > 0 )
         {
            final ViewGroup fragmentContainer = getFragmentContainer();
            final List< String > noteIds = task.getNoteIds();
            
            for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
            {
               final String noteId = noteIds.get( i );
               final View noteFragmentContainer = fragmentContainer.findViewWithTag( noteId );
               
               if ( noteFragmentContainer != null )
                  noteFragmentContainers.add( new NoteFragmentContainerState( noteId,
                                                                              noteFragmentContainer.getId() ) );
            }
         }
         
         if ( IsActivityInAddingNewNoteMode() )
            noteFragmentContainers.add( new NoteFragmentContainerState( NEW_NOTE_TEMPORARY_ID,
                                                                        NEW_NOTE_TEMPORARY_CONTAINER_ID ) );
         
         setConfiguredNoteFragmentContainers( noteFragmentContainers );
      }
   }
   
   
   
   private void restoreNoteFragmentContainers()
   {
      final List< NoteFragmentContainerState > noteFragmentContainers = getConfiguredNoteFragmentContainers();
      
      if ( noteFragmentContainers != null )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         
         for ( NoteFragmentContainerState noteFragmentContainer : noteFragmentContainers )
         {
            createAndAddNoteFragmentContainer( fragmentContainer,
                                               noteFragmentContainer.noteFragmentContainerId,
                                               noteFragmentContainer.noteId );
         }
      }
      
      setConfiguredNoteFragmentContainers( null );
   }
   
   
   
   @Override
   protected void takeConfigurationFrom( Bundle config )
   {
      super.takeConfigurationFrom( config );
      
      if ( config.containsKey( Config.EDIT_MODE_FRAG_ID ) )
         configuration.putInt( Config.EDIT_MODE_FRAG_ID,
                               config.getInt( Config.EDIT_MODE_FRAG_ID ) );
      
      if ( config.containsKey( Config.NOTE_FRAGMENT_CONTAINERS ) )
         configuration.putParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS,
                                               config.getParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS ) );
      
      if ( config.containsKey( Config.NOTE_ID_TO_DELETE ) )
         configuration.putString( Config.NOTE_ID_TO_DELETE,
                                  config.getString( Config.NOTE_ID_TO_DELETE ) );
   }
   
   
   
   public int getConfiguredEditModeFragmentId()
   {
      return configuration.getInt( Config.EDIT_MODE_FRAG_ID, 0 );
   }
   
   
   
   public void setConfiguredEditModeFragmentId( int fragmentId )
   {
      configuration.putInt( Config.EDIT_MODE_FRAG_ID, fragmentId );
   }
   
   
   
   public List< NoteFragmentContainerState > getConfiguredNoteFragmentContainers()
   {
      return configuration.getParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS );
   }
   
   
   
   public void setConfiguredNoteFragmentContainers( List< NoteFragmentContainerState > noteFragmentContainers )
   {
      if ( noteFragmentContainers != null )
         configuration.putParcelableArrayList( Config.NOTE_FRAGMENT_CONTAINERS,
                                               new ArrayList< NoteFragmentContainerState >( noteFragmentContainers ) );
      else
         configuration.remove( Config.NOTE_FRAGMENT_CONTAINERS );
   }
   
   
   
   private String getConfiguredNoteIdToDelete()
   {
      return configuration.getString( Config.NOTE_ID_TO_DELETE );
   }
   
   
   
   private void setConfiguredNoteIdToDelete( String noteIdToDelete )
   {
      if ( noteIdToDelete == null )
         configuration.remove( Config.NOTE_ID_TO_DELETE );
      else
         configuration.putString( Config.NOTE_ID_TO_DELETE, noteIdToDelete );
   }
   
   
   
   public String getTaskIdFromIntent()
   {
      String taskId = null;
      
      final Uri taskUri = getIntent().getData();
      
      if ( taskUri != null )
         taskId = taskUri.getLastPathSegment();
      
      return taskId;
   }
   
   
   
   public Task getTask()
   {
      Task task = null;
      
      final Fragment fragment = findAddedFragmentById( R.id.frag_task );
      
      if ( fragment instanceof TaskEditFragment )
      {
         task = ( (TaskEditFragment) fragment ).getConfiguredTaskAssertNotNull();
      }
      else if ( fragment instanceof TaskFragment )
      {
         task = ( (TaskFragment) fragment ).getLoaderData();
      }
      
      return task;
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      final Task task = getTask();
      
      if ( task == null )
         throw new IllegalStateException( "task must not be null" );
      
      return task;
   }
   
   
   
   public RtmTaskNote getNoteOfNoteFragment( String fragmentTag )
   {
      RtmTaskNote note = null;
      
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      
      if ( fragment instanceof NoteEditFragment )
      {
         note = ( (NoteEditFragment) fragment ).getConfiguredNoteAssertNotNull();
      }
      else if ( fragment instanceof NoteFragment )
      {
         note = ( (NoteFragment) fragment ).getNote();
      }
      
      return note;
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      super.onCreateOptionsMenu( menu );
      
      final Task task = getTask();
      
      final boolean hasRtmWriteAccess = AccountUtils.isWriteableAccess( this );
      final boolean isInEditMode = IsActivityInEditMode();
      final boolean taskCanBeEdited = task != null
         && canEditFragment( R.id.frag_task );
      
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.COMPLETE_TASK,
                                   getString( R.string.app_task_complete ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_complete,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   !isInEditMode && taskCanBeEdited
                                      && task.getCompleted() == null );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.UNCOMPLETE_TASK,
                                   getString( R.string.app_task_uncomplete ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_incomplete,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   !isInEditMode && taskCanBeEdited
                                      && task.getCompleted() != null );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.POSTPONE_TASK,
                                   getString( R.string.app_task_postpone ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_postponed,
                                   MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                   !isInEditMode && taskCanBeEdited );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.DELETE_TASK,
                                   getString( R.string.app_task_delete ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_trash,
                                   MenuItem.SHOW_AS_ACTION_IF_ROOM,
                                   !isInEditMode && taskCanBeEdited );
      
      // Do not check for task != null here cause this is also needed
      // when adding a new task. In this case the task is always null
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.SAVE,
                                   getString( R.string.app_save ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_disc,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   isInEditMode && hasRtmWriteAccess );
      UIUtils.addOptionalMenuItem( this,
                                   menu,
                                   OptionsMenu.ABORT,
                                   getString( R.string.phr_cancel_sync ),
                                   MenuCategory.NONE,
                                   Menu.NONE,
                                   R.drawable.ic_menu_cancel,
                                   MenuItem.SHOW_AS_ACTION_ALWAYS,
                                   isInEditMode && hasRtmWriteAccess );
      return true;
   }
   
   
   
   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      switch ( item.getItemId() )
      {
         case OptionsMenu.COMPLETE_TASK:
            TaskEditUtils.setTaskCompletion( this, getTaskAssertNotNull(), true );
            return true;
            
         case OptionsMenu.UNCOMPLETE_TASK:
            TaskEditUtils.setTaskCompletion( this,
                                             getTaskAssertNotNull(),
                                             false );
            return true;
            
         case OptionsMenu.POSTPONE_TASK:
            TaskEditUtils.postponeTask( this, getTaskAssertNotNull() );
            return true;
            
         case OptionsMenu.DELETE_TASK:
            onDeleteTask( getTaskAssertNotNull().getId() );
            return true;
            
         case OptionsMenu.SAVE:
            finishEditing( FinishEditMode.SAVE );
            return true;
            
         case OptionsMenu.ABORT:
            finishEditing( FinishEditMode.CANCELED );
            return true;
            
         default :
            return super.onOptionsItemSelected( item );
      }
   }
   
   
   
   public void onEditTask( View taskEditButton )
   {
      setActivityInEditMode( R.id.frag_task );
      createTaskEditFragment();
   }
   
   
   
   @Override
   public void onChangeTags( List< String > tags )
   {
      showChangeTagsDialog( createTaskEditChangeTagsConfiguration( tags ) );
   }
   
   
   
   @Override
   public void onEditDueByPicker()
   {
      MolokoCalendar due = getTaskEditFragment().getDue();
      
      if ( due == null || !due.hasDate() )
      {
         due = MolokoCalendar.getInstance();
         due.setHasTime( false );
      }
      
      DuePickerDialogFragment.show( this, due.getTimeInMillis(), due.hasTime() );
   }
   
   
   
   @Override
   public void onEditRecurrenceByPicker()
   {
      Pair< String, Boolean > recurrencePattern = getTaskEditFragment().getRecurrencePattern();
      
      if ( recurrencePattern == null )
         recurrencePattern = Pair.create( Strings.EMPTY_STRING, Boolean.FALSE );
      
      RecurrPickerDialogFragment.show( this,
                                       recurrencePattern.first,
                                       recurrencePattern.second );
   }
   
   
   
   @Override
   public void onEditEstimateByPicker()
   {
      final long estimateMillis = getTaskEditFragment().getEstimateMillis();
      
      EstimatePickerDialogFragment.show( this, estimateMillis );
   }
   
   
   
   @Override
   public boolean onFinishTaskEditingByInputMethod()
   {
      boolean finished = true;
      final Fragment fragment = findAddedFragmentById( R.id.frag_task );
      
      if ( fragment instanceof IEditFragment< ? > )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         finished = editFragment.onFinishEditing();
      }
      
      return finished;
   }
   
   
   
   public void onAddNote( View addNoteButton )
   {
      setActivityInEditMode( createAddNewNoteFragment( createAddNewNoteFragmentConfiguration( getTaskAssertNotNull().getTaskSeriesId() ) ) );
   }
   
   
   
   public void onEditNote( View noteEditButton )
   {
      final Fragment fragment = findAddedFragmentByTag( (String) noteEditButton.getTag() );
      
      if ( setFragmentInEditMode( fragment ) )
         setActivityInEditMode( fragment.getId() );
   }
   
   
   
   public void onDeleteNote( View noteDeleteButton )
   {
      setConfiguredNoteIdToDelete( (String) noteDeleteButton.getTag() );
      UIUtils.showDeleteElementDialog( this,
                                       getString( R.string.app_note ),
                                       DELETE_NOTE_DIALOG_TAG );
   }
   
   
   
   @Override
   protected void onReEvaluateRtmAccessLevel( Perms currentAccessLevel )
   {
      super.onReEvaluateRtmAccessLevel( currentAccessLevel );
      
      if ( !IsActivityInEditMode() )
      {
         showEditButtons( currentAccessLevel.allowsEditing() );
         invalidateOptionsMenu();
      }
   }
   
   
   
   @Override
   public void onBackPressed()
   {
      if ( IsActivityInEditMode() )
         finishEditing( FinishEditMode.CANCELED );
      else
         super.onBackPressed();
   }
   
   
   
   @Override
   protected boolean onFinishActivityByHome()
   {
      boolean finish = super.onFinishActivityByHome();
      
      if ( finish && IsActivityInEditMode() )
         finish = finishEditingAndFinishActivity( FinishEditMode.CANCELED );
      
      return finish;
   }
   
   
   
   public void onDeleteTask( String taskId )
   {
      final Task task = getTaskAssertNotNull();
      UIUtils.showDeleteElementDialog( this,
                                       task.getName(),
                                       DELETE_TASK_DIALOG_TAG );
   }
   
   
   
   @Override
   public void onOpenLocation( String locationId )
   {
      final Task task = getTaskAssertNotNull();
      LocationChooserDialogFragment.show( this, task );
   }
   
   
   
   @Override
   public void onOpenContact( String fullname, String username )
   {
      final Intent intent = Intents.createOpenContactIntent( this,
                                                             fullname,
                                                             username );
      
      // It is possible that we came here from the ContactsListActivity
      // by clicking a contact, clicking a task, clicking the contact again.
      // So we reorder the former contact's tasks list to front.
      intent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
      
      startActivity( intent );
   }
   
   
   
   @Override
   public void onAlertDialogFragmentClick( int dialogId, String tag, int which )
   {
      if ( dialogId == R.id.dlg_taskactivity_request_remove_note )
      {
         if ( which == Dialog.BUTTON_NEGATIVE )
         {
            finishAddingNewNote( FinishEditMode.FORCE_CANCELED );
            setActivityInEditMode( 0 );
         }
      }
      else
      {
         super.onAlertDialogFragmentClick( dialogId, tag, which );
      }
   }
   
   
   
   @Override
   protected void handleCancelWithChangesDialogClick( String tag, int which )
   {
      if ( which == Dialog.BUTTON_POSITIVE )
      {
         finishFragmentEditing( getConfiguredEditModeFragmentId(),
                                FinishEditMode.CANCELED );
         
         if ( tag.equals( FINISH_EDIT_W_CHANGES_END_EDITING ) )
         {
            setActivityInEditMode( 0 );
         }
         else if ( tag.equals( FINISH_EDIT_W_CHANGES_FINISH_ACTIVITY ) )
         {
            finish();
         }
      }
   }
   
   
   
   @Override
   protected void handleDeleteElementDialogClick( String tag, int which )
   {
      if ( tag.equals( DELETE_NOTE_DIALOG_TAG ) )
      {
         if ( which == Dialog.BUTTON_POSITIVE )
         {
            deleteNoteImpl( getConfiguredNoteIdToDelete() );
         }
         
         setConfiguredNoteIdToDelete( null );
      }
      else if ( tag.equals( DELETE_TASK_DIALOG_TAG ) )
      {
         if ( which == Dialog.BUTTON_POSITIVE )
         {
            final Task task = getTaskAssertNotNull();
            TaskEditUtils.deleteTask( TaskActivity.this, task );
            
            finish();
         }
      }
   }
   
   
   
   @Override
   public void onPickerDialogClosed( AbstractPickerDialogFragment dialog,
                                     CloseReason reason )
   {
      if ( reason == CloseReason.OK )
      {
         if ( dialog instanceof DuePickerDialogFragment )
         {
            final DuePickerDialogFragment frag = (DuePickerDialogFragment) dialog;
            getTaskEditFragment().setDue( frag.getCalendar() );
         }
         else if ( dialog instanceof RecurrPickerDialogFragment )
         {
            final RecurrPickerDialogFragment frag = (RecurrPickerDialogFragment) dialog;
            getTaskEditFragment().setRecurrencePattern( frag.getPattern() );
         }
         else if ( dialog instanceof EstimatePickerDialogFragment )
         {
            final EstimatePickerDialogFragment frag = (EstimatePickerDialogFragment) dialog;
            getTaskEditFragment().setEstimateMillis( frag.getMillis() );
         }
      }
   }
   
   
   
   private void deleteNoteImpl( String noteId )
   {
      final Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications = NoteEditUtils.deleteNote( this,
                                                                                                              noteId );
      if ( applyModifications( modifications ) )
      {
         removeNoteFragmentByNoteId( noteId,
                                     FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
      }
   }
   
   
   
   private void setActivityInEditMode( int editFragmentId )
   {
      setConfiguredEditModeFragmentId( editFragmentId );
      
      showEditButtons( editFragmentId != 0 ? false : true );
      invalidateOptionsMenu();
   }
   
   
   
   private boolean IsActivityInEditMode()
   {
      return getConfiguredEditModeFragmentId() != 0;
   }
   
   
   
   private boolean IsActivityInAddingNewNoteMode()
   {
      return getConfiguredEditModeFragmentId() == NEW_NOTE_TEMPORARY_CONTAINER_ID;
   }
   
   
   
   private boolean finishEditing( FinishEditMode how )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( how == FinishEditMode.CANCELED
         && isEditFragmentModified( getConfiguredEditModeFragmentId() ) )
      {
         finishEditingShowCancelDialog( FINISH_EDIT_W_CHANGES_END_EDITING );
         return false;
      }
      else
      {
         if ( finishFragmentEditing( getConfiguredEditModeFragmentId(), how ) )
            setActivityInEditMode( 0 );
         
         return true;
      }
   }
   
   
   
   private boolean finishEditingAndFinishActivity( FinishEditMode how )
   {
      if ( !IsActivityInEditMode() )
         throw new IllegalStateException( "expected to be in edit mode" );
      
      if ( how == FinishEditMode.CANCELED
         && isEditFragmentModified( getConfiguredEditModeFragmentId() ) )
      {
         finishEditingShowCancelDialog( FINISH_EDIT_W_CHANGES_FINISH_ACTIVITY );
         return false;
      }
      else
      {
         // Do not call finish() here since the caller will finish if we return
         // true
         return finishFragmentEditing( getConfiguredEditModeFragmentId(), how );
      }
   }
   
   
   
   private void finishEditingShowCancelDialog( String how )
   {
      requestCancelEditing( how );
   }
   
   
   
   private boolean setFragmentInEditMode( Fragment fragment )
   {
      if ( fragment instanceof IEditableFragment< ? > )
      {
         final IEditableFragment< ? > editableFragment = (IEditableFragment< ? >) fragment;
         
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.replace( fragment.getId(),
                              (Fragment) editableFragment.createEditFragmentInstance(),
                              fragment.getTag() );
         transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
         
         transaction.commit();
         
         return true;
      }
      
      return false;
   }
   
   
   
   private boolean isEditFragmentModified( int fragmentId )
   {
      boolean hasChanges = false;
      
      final Fragment fragment = findAddedFragmentById( fragmentId );
      
      if ( fragment instanceof IEditFragment< ? > )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         hasChanges = editFragment.hasChanges();
      }
      
      return hasChanges;
   }
   
   
   
   private boolean finishFragmentEditing( int fragmentContainerId,
                                          FinishEditMode how )
   {
      if ( IsActivityInAddingNewNoteMode() )
         return finishAddingNewNote( how );
      else
         return finishFragmentEditingImpl( fragmentContainerId, how );
   }
   
   
   
   private boolean finishFragmentEditingImpl( int fragmentContainerId,
                                              FinishEditMode how )
   {
      boolean finished = true;
      
      final Fragment fragment = findAddedFragmentById( fragmentContainerId );
      
      if ( fragment instanceof IEditFragment< ? > )
      {
         final IEditFragment< ? > editFragment = (IEditFragment< ? >) fragment;
         
         if ( how == FinishEditMode.SAVE )
            finished = editFragment.onFinishEditing();
         else
            editFragment.onCancelEditing();
         
         if ( finished )
         {
            final Fragment editableFragment = (Fragment) editFragment.createEditableFragmentInstance();
            
            if ( editableFragment != null )
            {
               final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               
               transaction.replace( fragment.getId(),
                                    editableFragment,
                                    fragment.getTag() );
               transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
               
               transaction.commit();
            }
            else
            {
               finish();
            }
         }
      }
      
      return finished;
   }
   
   
   
   private void removeNoteFragmentByNoteId( String noteId, int transit )
   {
      if ( removeFragmentByTag( noteId, transit ) )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         final View taskNoteLayout = fragmentContainer.findViewWithTag( createTaskNoteLayoutTag( noteId ) );
         
         if ( taskNoteLayout != null )
            fragmentContainer.removeView( taskNoteLayout );
      }
   }
   
   
   
   private void showEditButtons( boolean show )
   {
      showTaskEditButtons( show );
      
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      for ( int i = 0, cnt = fragmentContainer.getChildCount(); i < cnt; ++i )
      {
         final View view = fragmentContainer.getChildAt( i );
         final Object tag = view.getTag();
         
         if ( tag instanceof String
            && ( (String) tag ).startsWith( TASK_NOTE_LAYOUT_TAG_STUB ) )
         {
            showNoteEditButtonsOfNoteFragment( (String) tag, show );
         }
      }
   }
   
   
   
   private void showTaskEditButtons( boolean show )
   {
      show = show && canEditFragment( R.id.frag_task );
      findViewById( R.id.task_buttons ).setVisibility( show ? View.VISIBLE
                                                           : View.GONE );
   }
   
   
   
   private void showNoteEditButtonsOfNoteFragment( String taskNoteLayoutTag,
                                                   boolean show )
   {
      show = show
         && canEditFragment( getNoteIdFromTaskNoteLayoutTag( taskNoteLayoutTag ) );
      
      final View taskNoteLayout = getFragmentContainer().findViewWithTag( taskNoteLayoutTag );
      
      if ( taskNoteLayout != null )
      {
         final View buttonsContainer = taskNoteLayout.findViewById( R.id.note_buttons );
         if ( buttonsContainer != null )
         {
            buttonsContainer.setVisibility( show ? View.VISIBLE : View.GONE );
         }
      }
   }
   
   
   
   private void createTaskFragment()
   {
      final Fragment taskFragment = findAddedFragmentById( R.id.frag_task );
      
      if ( taskFragment == null )
         createInitialTaskFragmentByIntent( getIntent() );
      else if ( R.id.frag_task != getConfiguredEditModeFragmentId() )
         updateTaskFragment();
   }
   
   
   
   private void createInitialTaskFragmentByIntent( Intent intent )
   {
      final Fragment fragment = TaskFragmentFactory.newFragment( this,
                                                                 getIntent(),
                                                                 createTaskFragmentConfiguration( getTaskIdFromIntent() ) );
      if ( fragment != null )
      {
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         transaction.add( R.id.frag_task, fragment, createTaskFragmentTag() );
         
         transaction.commit();
         
         if ( fragment instanceof IEditFragment< ? > )
            setConfiguredEditModeFragmentId( R.id.frag_task );
      }
   }
   
   
   
   private void updateTaskFragment()
   {
      final Fragment fragment = TaskFragment.newInstance( createTaskFragmentConfiguration( getTaskIdFromIntent() ) );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.replace( R.id.frag_task, fragment, createTaskFragmentTag() );
      
      transaction.commit();
   }
   
   
   
   private void createTaskEditFragment()
   {
      final Fragment fragment = TaskEditFragment.newInstance( createTaskEditFragmentConfiguration( getTaskAssertNotNull() ) );
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      
      transaction.replace( R.id.frag_task, fragment, createTaskFragmentTag() );
      
      transaction.commit();
   }
   
   
   
   private Bundle createTaskFragmentConfiguration( String taskId )
   {
      final Bundle config = getFragmentConfigurations( R.id.frag_task );
      
      config.putString( TaskFragment.Config.TASK_ID, taskId );
      
      return config;
   }
   
   
   
   private Bundle createTaskEditFragmentConfiguration( Task task )
   {
      final Bundle config = getFragmentConfigurations( R.id.frag_task );
      
      config.putParcelable( TaskEditFragment.Config.TASK, task );
      
      return config;
   }
   
   
   
   private String createTaskFragmentTag()
   {
      return String.valueOf( R.id.frag_task );
   }
   
   
   
   private Bundle createTaskEditChangeTagsConfiguration( List< String > tags )
   {
      final Bundle config = new Bundle( 2 );
      
      config.putStringArrayList( ChangeTagsDialogFragment.Config.TAGS,
                                 new ArrayList< String >( tags ) );
      
      return config;
   }
   
   
   
   private void showNoteFragmentsOfTask( Task task )
   {
      removeDeletedNoteFragmentsOfTask( task );
      
      if ( task.getNumberOfNotes() > 0 )
      {
         final ViewGroup fragmentContainer = getFragmentContainer();
         final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         
         final List< String > noteIds = task.getNoteIds();
         
         for ( int i = 0, cnt = noteIds.size(); i < cnt; ++i )
         {
            final String noteId = noteIds.get( i );
            final Fragment noteFragment = findAddedFragmentByTag( noteId );
            final int noteFragmentContainerId = createNoteFragmentContainerId( noteId );
            
            if ( noteFragment == null )
            {
               createNoteFragmentAndContainerFromId( fragmentContainer,
                                                     noteFragmentContainerId,
                                                     noteId,
                                                     transaction );
            }
            else if ( noteFragment.getId() != getConfiguredEditModeFragmentId() )
            {
               updateNoteFragment( noteFragment, transaction );
            }
         }
         
         transaction.commit();
      }
   }
   
   
   
   private void createNoteFragmentAndContainerFromId( ViewGroup fragmentContainer,
                                                      int fragmentContainerId,
                                                      String noteId,
                                                      FragmentTransaction transaction )
   {
      createAndAddNoteFragmentContainer( fragmentContainer,
                                         fragmentContainerId,
                                         noteId );
      
      final NoteFragment noteFragment = createNoteFragment( noteId );
      
      transaction.add( fragmentContainerId, noteFragment, noteId );
   }
   
   
   
   private NoteFragment createNoteFragment( String noteId )
   {
      final NoteFragment noteFragment = NoteFragment.newInstance( createNoteFragmentConfiguration( noteId ) );
      
      // If the Task changes, it will update the notes. This prevents "Note not found errors" for deleted
      // notes if the NoteFragment updates itself.
      noteFragment.setRespectContentChanges( false );
      
      return noteFragment;
   }
   
   
   
   private void updateNoteFragment( Fragment oldFragment,
                                    FragmentTransaction transaction )
   {
      final NoteFragment noteFragment = createNoteFragment( oldFragment.getTag() );
      transaction.replace( oldFragment.getId(),
                           noteFragment,
                           oldFragment.getTag() );
   }
   
   
   
   private void removeDeletedNoteFragmentsOfTask( Task task )
   {
      final List< String > noteIds = task.getNoteIds();
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      // getChildCount() has to be evaluated in every iteration cause we may delete views here.
      for ( int i = 0; i < fragmentContainer.getChildCount(); ++i )
      {
         final View view = fragmentContainer.getChildAt( i );
         final Object tag = view.getTag();
         
         if ( tag instanceof String
            && ( (String) tag ).startsWith( TASK_NOTE_LAYOUT_TAG_STUB ) )
         {
            final ViewGroup taskNoteLayout = (ViewGroup) view;
            
            boolean found = false;
            for ( int j = 0, cntTaskNoteChilds = taskNoteLayout.getChildCount(); j < cntTaskNoteChilds
               && !found; ++j )
            {
               final View taskNoteChild = taskNoteLayout.getChildAt( j );
               final Object taskNoteChildTag = taskNoteChild.getTag();
               
               if ( taskNoteChildTag instanceof String )
               {
                  found = true;
                  
                  final String addedNoteId = (String) taskNoteChildTag;
                  final int noteFragmentContainerId = taskNoteChild.getId();
                  
                  if ( noteFragmentContainerId != NEW_NOTE_TEMPORARY_CONTAINER_ID
                     && !noteIds.contains( addedNoteId ) )
                  {
                     if ( noteFragmentContainerId == getConfiguredEditModeFragmentId() )
                     {
                        requestRemovingEditNoteFragment( addedNoteId );
                     }
                     else
                     {
                        removeNoteFragmentByNoteId( addedNoteId,
                                                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
                     }
                  }
               }
            }
         }
      }
   }
   
   
   
   private int createAddNewNoteFragment( Bundle fragmentConfig )
   {
      final int noteFragmentContainerId = createAddNewNoteFragmentContainer();
      
      final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      final Fragment fragment = NoteAddFragment.newInstance( fragmentConfig );
      
      transaction.add( noteFragmentContainerId, fragment, NEW_NOTE_TEMPORARY_ID );
      transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
      
      transaction.commit();
      
      return noteFragmentContainerId;
   }
   
   
   
   private void replaceEditNoteFragmentWithAddNoteFragment( String noteId )
   {
      final Task task = getTaskAssertNotNull();
      
      final NoteEditFragment noteEditFragment = (NoteEditFragment) findAddedFragmentByTag( noteId );
      final String currentTitle = noteEditFragment.getNoteTitle();
      final String currentText = noteEditFragment.getNoteText();
      
      removeNoteFragmentByNoteId( noteId, FragmentTransaction.TRANSIT_NONE );
      
      setActivityInEditMode( createAddNewNoteFragment( createAddNewNoteFragmentConfiguration( task.getTaskSeriesId(),
                                                                                              currentTitle,
                                                                                              currentText ) ) );
   }
   
   
   
   private int createAddNewNoteFragmentContainer()
   {
      final ViewGroup fragmentContainer = getFragmentContainer();
      
      createAndAddNoteFragmentContainer( fragmentContainer,
                                         NEW_NOTE_TEMPORARY_CONTAINER_ID,
                                         NEW_NOTE_TEMPORARY_ID );
      
      return NEW_NOTE_TEMPORARY_CONTAINER_ID;
   }
   
   
   
   private Bundle createAddNewNoteFragmentConfiguration( String taskSeriesId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteAddFragment.Config.TASKSERIES_ID, taskSeriesId );
      
      return config;
   }
   
   
   
   private Bundle createAddNewNoteFragmentConfiguration( String taskSeriesId,
                                                         String noteTitle,
                                                         String noteText )
   {
      final Bundle config = createAddNewNoteFragmentConfiguration( taskSeriesId );
      
      if ( !TextUtils.isEmpty( noteTitle ) )
         config.putString( NoteAddFragment.Config.NEW_NOTE_TITLE, noteTitle );
      if ( !TextUtils.isEmpty( noteText ) )
         config.putString( NoteAddFragment.Config.NEW_NOTE_TEXT, noteText );
      
      return config;
   }
   
   
   
   private boolean finishAddingNewNote( FinishEditMode how )
   {
      final IEditFragment< ? > addNewNoteFragment = (IEditFragment< ? >) findAddedFragmentByTag( NEW_NOTE_TEMPORARY_ID );
      
      boolean ok = true;
      
      if ( how == FinishEditMode.SAVE )
         ok = addNewNoteFragment.onFinishEditing();
      else
         addNewNoteFragment.onCancelEditing();
      
      if ( ok )
         removeNoteFragmentByNoteId( NEW_NOTE_TEMPORARY_ID,
                                     FragmentTransaction.TRANSIT_FRAGMENT_CLOSE );
      
      return ok;
   }
   
   
   
   private View createAndAddNoteFragmentContainer( ViewGroup fragmentContainer,
                                                   int fragmentContainerId,
                                                   String noteId )
   {
      final View taskNoteLayout = getLayoutInflater().inflate( R.layout.task_note,
                                                               fragmentContainer,
                                                               false );
      taskNoteLayout.setTag( createTaskNoteLayoutTag( noteId ) );
      
      final View noteFragContainer = taskNoteLayout.findViewById( R.id.note_fragment_container );
      noteFragContainer.setId( fragmentContainerId );
      noteFragContainer.setTag( noteId );
      
      final RtmAuth.Perms accessLevel = AccountUtils.getAccessLevel( this );
      
      final boolean showNoteButtons = accessLevel.allowsEditing()
         && !IsActivityInEditMode();
      
      taskNoteLayout.findViewById( R.id.note_buttons )
                    .setVisibility( showNoteButtons ? View.VISIBLE : View.GONE );
      
      final View editNoteButton = taskNoteLayout.findViewById( R.id.note_buttons_edit );
      editNoteButton.setTag( noteId );
      
      final View deleteNoteButton = taskNoteLayout.findViewById( R.id.note_buttons_delete );
      deleteNoteButton.setTag( noteId );
      
      fragmentContainer.addView( taskNoteLayout );
      
      return noteFragContainer;
   }
   
   
   
   private void requestRemovingEditNoteFragment( final String noteId )
   {
      replaceEditNoteFragmentWithAddNoteFragment( noteId );
      
      new AlertDialogFragment.Builder( R.id.dlg_taskactivity_request_remove_note ).setMessage( getString( R.string.task_dlg_removing_editing_note ) )
                                                                                  .setPositiveButton( R.string.btn_edit )
                                                                                  .setNegativeButton( R.string.btn_delete )
                                                                                  .show( this );
   }
   
   
   
   private String createTaskNoteLayoutTag( String fragmentTag )
   {
      return TASK_NOTE_LAYOUT_TAG_STUB + fragmentTag;
   }
   
   
   
   private String getNoteIdFromTaskNoteLayoutTag( String taskNoteLayoutTag )
   {
      return taskNoteLayoutTag.substring( TASK_NOTE_LAYOUT_TAG_STUB.length() );
   }
   
   
   
   private int createNoteFragmentContainerId( String noteId )
   {
      return Integer.parseInt( noteId );
   }
   
   
   
   private Bundle createNoteFragmentConfiguration( String noteId )
   {
      final Bundle config = new Bundle();
      
      config.putString( NoteFragment.Config.NOTE_ID, noteId );
      
      return config;
   }
   
   
   
   private void showChangeTagsDialog( Bundle config )
   {
      final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      final DialogFragment newFragment = ChangeTagsDialogFragment.newInstance( config );
      
      newFragment.show( fragmentTransaction,
                        String.valueOf( R.id.frag_change_tags ) );
   }
   
   
   
   @Override
   public void onTagsChanged( List< String > tags )
   {
      final AbstractTaskEditFragment< ? > taskEditFragment = getTaskEditFragment();
      taskEditFragment.setTags( tags );
   }
   
   
   
   private void setPriorityBarVisibility()
   {
      final View taskFragmentContainer = findViewById( R.id.frag_task );
      final View priorityBar = taskFragmentContainer.findViewById( R.id.task_overview_priority_bar );
      final Task task = getTask();
      
      final boolean prioBarVisible = task != null
         && R.id.frag_task != getConfiguredEditModeFragmentId();
      
      priorityBar.setVisibility( prioBarVisible ? View.VISIBLE : View.GONE );
      
      if ( prioBarVisible )
         UIUtils.setPriorityColor( priorityBar, task );
   }
   
   
   
   private ViewGroup getFragmentContainer()
   {
      return (ViewGroup) findViewById( R.id.fragment_container );
   }
   
   
   
   private boolean canEditFragment( int fragId )
   {
      boolean canEdit = false;
      
      final Fragment fragment = findAddedFragmentById( fragId );
      canEdit = ( fragment instanceof IEditableFragment< ? > )
         && ( (IEditableFragment< ? >) fragment ).canBeEdited();
      
      return canEdit;
   }
   
   
   
   private boolean canEditFragment( String fragmentTag )
   {
      boolean canEdit = false;
      
      final Fragment fragment = findAddedFragmentByTag( fragmentTag );
      canEdit = ( fragment instanceof IEditableFragment< ? > )
         && ( (IEditableFragment< ? >) fragment ).canBeEdited();
      
      return canEdit;
   }
   
   
   
   private AbstractTaskEditFragment< ? > getTaskEditFragment() throws AssertionError
   {
      if ( getConfiguredEditModeFragmentId() != R.id.frag_task )
         throw new AssertionError( "expected to be in task editing mode" );
      
      final AbstractTaskEditFragment< ? > taskEditFragment = (AbstractTaskEditFragment< ? >) findAddedFragmentById( R.id.frag_task );
      return taskEditFragment;
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return null;
   }
   
   
   
   @Override
   public void onFragmentLoadStarted( int fragmentId, String fragmentTag )
   {
   }
   
   
   
   @Override
   public void onFragmentLoadFinished( final int fragmentId,
                                       final String fragmentTag,
                                       final boolean success )
   {
      if ( fragmentId == R.id.frag_task )
      {
         handler.postAtFrontOfQueue( new Runnable()
         {
            @Override
            public void run()
            {
               final Task task = getTask();
               
               if ( task != null )
                  showNoteFragmentsOfTask( task );
               
               setPriorityBarVisibility();
               showTaskEditButtons( !IsActivityInEditMode() && task != null );
               invalidateOptionsMenu();
            }
         } );
      }
      else if ( fragmentId != NEW_NOTE_TEMPORARY_CONTAINER_ID )
      {
         handler.postAtFrontOfQueue( new Runnable()
         {
            @Override
            public void run()
            {
               showNoteEditButtonsOfNoteFragment( createTaskNoteLayoutTag( fragmentTag ),
                                                  !IsActivityInEditMode()
                                                     && success );
            }
         } );
      }
   }
}

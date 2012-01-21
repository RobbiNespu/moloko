/* 
 *	Copyright (c) 2012 Ronny R�hricht
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

package dev.drsoran.moloko.util;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.ContentProviderAction;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.Task;


public final class TaskEditUtils
{
   private TaskEditUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > setTaskCompletion( FragmentActivity activity,
                                                                                                  Task task,
                                                                                                  boolean complete )
   {
      return setTasksCompletion( activity,
                                 Collections.singletonList( task ),
                                 complete );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > setTasksCompletion( FragmentActivity activity,
                                                                                                   List< ? extends Task > tasks,
                                                                                                   boolean complete )
   {
      final ModificationSet modifications = new ModificationSet();
      
      for ( Task task : tasks )
      {
         final Date complDate = task.getCompleted();
         
         if ( complDate == null && complete || complDate != null && !complete )
         {
            modifications.add( Modification.newModification( RawTasks.CONTENT_URI,
                                                             task.getId(),
                                                             RawTasks.COMPLETED_DATE,
                                                             complete
                                                                     ? System.currentTimeMillis()
                                                                     : null ) );
            
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
         }
      }
      
      return Pair.create( modifications.toContentProviderActionItemList(),
                          new ApplyChangesInfo( activity.getString( R.string.toast_save_task ),
                                                activity.getResources()
                                                        .getQuantityString( complete
                                                                                    ? R.plurals.toast_completed_task
                                                                                    : R.plurals.toast_incompleted_task,
                                                                            tasks.size(),
                                                                            tasks.size() ),
                                                activity.getString( R.string.toast_save_task_failed ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > postponeTask( FragmentActivity activity,
                                                                                             Task task )
   {
      return postponeTasks( activity, Collections.singletonList( task ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > postponeTasks( FragmentActivity activity,
                                                                                              List< ? extends Task > tasks )
   {
      /**
       * NOTE: RTM has no API to set the postponed count. One can only postpone a task and the count is the number of
       * calls, which affects also the due date of the task. Since we want offline postponing a task, we must alter the
       * due date only locally, but not sync it out. Finally the date becomes in sync with RTM if we call the postpone
       * API method and using the returned result task.
       * 
       * All following due date modifications are non-persistent. Means they only appear locally but are not going to be
       * synced out.
       **/
      
      final ModificationSet modifications = new ModificationSet();
      
      if ( !tasks.isEmpty() )
      {
         final MolokoCalendar cal = MolokoCalendar.getUTCInstance();
         
         for ( Task task : tasks )
         {
            final Date due = task.getDue();
            cal.setTimeInMillis( System.currentTimeMillis() );
            
            // If the task has no due date or is overdue, its due date is set to today.
            if ( due == null )
            {
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            else if ( MolokoDateUtils.isBefore( due.getTime(),
                                                cal.getTimeInMillis() ) )
            {
               final MolokoCalendar tmp = MolokoDateUtils.newCalendarUTC( due.getTime() );
               
               // Preserve the original time when setting to today
               cal.set( Calendar.HOUR_OF_DAY, tmp.get( Calendar.HOUR_OF_DAY ) );
               cal.set( Calendar.HOUR, tmp.get( Calendar.HOUR ) );
               cal.set( Calendar.MINUTE, tmp.get( Calendar.MINUTE ) );
               cal.set( Calendar.SECOND, tmp.get( Calendar.SECOND ) );
               cal.set( Calendar.MILLISECOND, 0 );
               
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            
            // Otherwise, the task due date is advanced a day.
            else
            {
               cal.setTime( due );
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
               modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                             task.getId(),
                                                                             RawTasks.DUE_DATE,
                                                                             cal.getTimeInMillis() ) );
            }
            
            modifications.add( Modification.newModification( RawTasks.CONTENT_URI,
                                                             task.getId(),
                                                             RawTasks.POSTPONED,
                                                             task.getPosponed() + 1 ) );
            
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
         }
      }
      
      return Pair.create( modifications.toContentProviderActionItemList(),
                          new ApplyChangesInfo( activity.getString( R.string.toast_save_task ),
                                                activity.getResources()
                                                        .getQuantityString( R.plurals.toast_postponed_task,
                                                                            tasks.size(),
                                                                            tasks.size() ),
                                                activity.getString( R.string.toast_save_task_failed ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > insertTask( FragmentActivity activity,
                                                                                           Task task )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      boolean ok = actionItemList.addAll( ContentProviderAction.Type.INSERT,
                                          TasksProviderPart.insertLocalCreatedTask( task ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                             task.getTaskSeriesId() ),
                                                                   task.getCreated()
                                                                       .getTime() ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                             task.getId() ),
                                                                   task.getCreated()
                                                                       .getTime() ) );
      
      if ( !ok )
         actionItemList = null;
      
      return Pair.create( actionItemList,
                          new ApplyChangesInfo( activity.getString( R.string.toast_insert_task ),
                                                activity.getString( R.string.toast_insert_task_ok ),
                                                activity.getString( R.string.toast_insert_task_fail ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > deleteTask( FragmentActivity activity,
                                                                                           Task task )
   {
      return deleteTasks( activity, Collections.singletonList( task ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > deleteTasks( FragmentActivity activity,
                                                                                            List< ? extends Task > tasks )
   {
      boolean ok = true;
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      if ( !tasks.isEmpty() )
      {
         final ModificationSet modifications = new ModificationSet();
         
         for ( Task task : tasks )
         {
            modifications.add( Modification.newNonPersistentModification( RawTasks.CONTENT_URI,
                                                                          task.getId(),
                                                                          RawTasks.DELETED_DATE,
                                                                          System.currentTimeMillis() ) );
            
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
            
            ok = actionItemList.add( ContentProviderAction.Type.DELETE,
                                     CreationsProviderPart.deleteCreation( TaskSeries.CONTENT_URI,
                                                                           task.getTaskSeriesId() ) );
            ok = ok
               && actionItemList.add( ContentProviderAction.Type.DELETE,
                                      CreationsProviderPart.deleteCreation( RawTasks.CONTENT_URI,
                                                                            task.getId() ) );
         }
         
         actionItemList.add( 0, modifications );
      }
      
      if ( !ok )
         actionItemList = null;
      
      return Pair.create( actionItemList,
                          new ApplyChangesInfo( activity.getString( R.string.toast_delete_task ),
                                                activity.getResources()
                                                        .getQuantityString( R.plurals.toast_deleted_task,
                                                                            tasks.size(),
                                                                            tasks.size() ),
                                                activity.getString( R.string.toast_delete_task_failed ) ) );
   }
}

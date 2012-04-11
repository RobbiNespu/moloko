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

package dev.drsoran.moloko.fragments;

import java.util.Collections;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.IEditableFragment;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.annotations.InstanceState;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public class TaskEditFragment extends
         AbstractTaskEditFragment< TaskEditFragment >
{
   private final static IntentFilter INTENT_FILTER;
   
   static
   {
      try
      {
         INTENT_FILTER = new IntentFilter( Intent.ACTION_EDIT,
                                           "vnd.android.cursor.item/vnd.rtm.task" );
         INTENT_FILTER.addCategory( Intent.CATEGORY_DEFAULT );
      }
      catch ( MalformedMimeTypeException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   public final static TaskEditFragment newInstance( Bundle config )
   {
      final TaskEditFragment fragment = new TaskEditFragment();
      
      fragment.setArguments( config );
      
      return fragment;
   }
   
   @InstanceState( key = Intents.Extras.KEY_TASK )
   private Task task;
   
   
   
   public TaskEditFragment()
   {
      registerAnnotatedConfiguredInstance( this, TaskEditFragment.class );
   }
   
   
   
   public static IntentFilter getIntentFilter()
   {
      return INTENT_FILTER;
   }
   
   
   
   @Override
   protected Bundle getInitialValues()
   {
      final Task task = getTaskAssertNotNull();
      
      final Bundle initialValues = new Bundle();
      
      initialValues.putString( Tasks.TASKSERIES_NAME, task.getName() );
      initialValues.putString( Tasks.LIST_ID, task.getListId() );
      initialValues.putString( Tasks.PRIORITY,
                               RtmTask.convertPriority( task.getPriority() ) );
      initialValues.putString( Tasks.TAGS,
                               TextUtils.join( Tasks.TAGS_SEPARATOR,
                                               task.getTags() ) );
      initialValues.putLong( Tasks.DUE_DATE,
                             MolokoDateUtils.getTime( task.getDue(),
                                                      Long.valueOf( -1 ) ) );
      initialValues.putBoolean( Tasks.HAS_DUE_TIME, task.hasDueTime() );
      initialValues.putString( Tasks.RECURRENCE, task.getRecurrence() );
      initialValues.putBoolean( Tasks.RECURRENCE_EVERY,
                                task.isEveryRecurrence() );
      initialValues.putString( Tasks.ESTIMATE, task.getEstimate() );
      initialValues.putLong( Tasks.ESTIMATE_MILLIS, task.getEstimateMillis() );
      initialValues.putString( Tasks.LOCATION_ID, task.getLocationId() );
      initialValues.putString( Tasks.URL, task.getUrl() );
      
      return initialValues;
   }
   
   
   
   @Override
   protected void initializeHeadSection()
   {
      final Task task = getTaskAssertNotNull();
      
      defaultInitializeHeadSectionImpl( task );
   }
   
   
   
   @Override
   protected void registerInputListeners()
   {
      super.registerInputListeners();
      
      getContentView().findViewById( R.id.task_edit_tags_btn_change )
                      .setOnClickListener( new OnClickListener()
                      {
                         @Override
                         public void onClick( View v )
                         {
                            listener.onChangeTags( getTags() );
                         }
                      } );
   }
   
   
   
   public Task getTaskAssertNotNull()
   {
      if ( task == null )
         throw new AssertionError( "expected task to be not null" );
      
      return task;
   }
   
   
   
   @Override
   protected boolean saveChanges()
   {
      boolean ok = super.saveChanges();
      
      if ( ok )
      {
         final ModificationSet modifications = createModificationSet( Collections.singletonList( getTaskAssertNotNull() ) );
         
         if ( modifications != null && modifications.size() > 0 )
         {
            ok = applyModifications( modifications );
         }
      }
      
      return ok;
   }
   
   
   
   @Override
   public IEditableFragment< ? extends Fragment > createEditableFragmentInstance()
   {
      final Bundle config = new Bundle();
      
      config.putString( TaskFragment.Config.TASK_ID,
                        getTaskAssertNotNull().getId() );
      
      final TaskFragment fragment = TaskFragment.newInstance( config );
      return fragment;
   }
}
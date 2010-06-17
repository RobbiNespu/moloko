package dev.drsoran.moloko.service.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.SyncResult;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.content.Queries;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public final class RtmTasksSync
{
   private final static String TAG = RtmTasksSync.class.getSimpleName();
   
   

   public static boolean in_computeSync( ContentProviderClient provider,
                                         ServiceImpl service,
                                         SyncResult syncResult,
                                         ArrayList< ContentProviderOperation > operations ) throws RemoteException
   {
      // Get all lists from local database
      final RtmTasks local_Tasks = RtmTaskSeriesProviderPart.getAllTaskSeries( provider );
      
      if ( local_Tasks == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local tasks failed." );
         return false;
      }
      
      RtmTasks server_Tasks = null;
      
      try
      {
         server_Tasks = service.tasks_getList( null, null, null );
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         return false;
      }
      
      boolean ok = true;
      
      // Sync task lists
      final ContentProviderSyncableList< RtmTaskList > local_SyncList = new ContentProviderSyncableList< RtmTaskList >( provider,
                                                                                                                        local_Tasks.getLists(),
                                                                                                                        RtmTaskList.LESS_ID );
      
      final ArrayList< ISyncOperation > syncOperations = SyncDiffer.diff( server_Tasks.getLists(),
                                                                          local_SyncList );
      
      ok = syncOperations != null;
      
      if ( ok )
      {
         for ( Iterator< ISyncOperation > sop = syncOperations.iterator(); ok
            && sop.hasNext(); )
         {
            // TODO: Do not execute, get all operations and store them
            ok = sop.next().execute( syncResult );
         }
      }
      
      return ok;
   }
   


   private static boolean in_syncRtmTaskList( ContentProviderClient provider,
                                              RtmTaskList lhs,
                                              RtmTaskList rhs,
                                              ArrayList< ContentProviderOperation > operations,
                                              SyncResult result ) throws RemoteException
   {
      boolean ok = true;
      
      final List< RtmTaskSeries > rhs_TaskSeriesList = rhs.getSeries();
      
      // Here we have to clone the list cause the returned list can not be modified nor
      // sorted.
      final ArrayList< RtmTaskSeries > lhs_TaskSeriesList = new ArrayList< RtmTaskSeries >();
      lhs_TaskSeriesList.addAll( lhs.getSeries() );
      
      // Sort the reference list by their taskseries IDs.
      Collections.sort( lhs_TaskSeriesList, RtmTaskSeries.LESS_ID );
      
      // For each element from the reference list
      for ( Iterator< RtmTaskSeries > i = rhs_TaskSeriesList.iterator(); ok
         && i.hasNext(); )
      {
         final RtmTaskSeries rhs_RtmTaskSeries = i.next();
         
         final int idx = Collections.binarySearch( lhs_TaskSeriesList,
                                                   rhs_RtmTaskSeries,
                                                   RtmTaskSeries.LESS_ID );
         
         // INSERT: if we do not have the taskseries in the lhs list
         if ( idx < 0 )
         {
            final List< ContentProviderOperation > insertOperations = RtmTaskSeriesProviderPart.insertTaskSeries( provider,
                                                                                                                  lhs.getId(),
                                                                                                                  rhs_RtmTaskSeries );
            ok = insertOperations != null;
            if ( ok && operations.addAll( insertOperations ) )
               ++result.stats.numInserts;
         }
         
         // UPDATE: if we have the taskseries in the lhs list check if content changed
         else
         {
            final RtmTaskSeries lhs_RtmTaskSeries = lhs_TaskSeriesList.get( idx );
            final ArrayList< ContentProviderOperation > updateOperations = new ArrayList< ContentProviderOperation >();
            
            ok = in_syncRtmTaskSeries( provider,
                                       lhs_RtmTaskSeries,
                                       rhs_RtmTaskSeries,
                                       lhs.getId(),
                                       updateOperations,
                                       result );
            
            if ( ok && operations.addAll( updateOperations ) )
               ++result.stats.numUpdates;
         }
      }
      
      return ok;
   }
   


   private static boolean in_syncRtmTaskSeries( ContentProviderClient provider,
                                                RtmTaskSeries lhs,
                                                RtmTaskSeries rhs,
                                                String listId,
                                                ArrayList< ContentProviderOperation > operations,
                                                SyncResult result ) throws RemoteException
   {
      boolean ok = lhs.getId().equals( rhs.getId() );
      
      if ( ok )
      {
         ok = in_SyncRtmTask( provider,
                              lhs.getTask(),
                              rhs.getTask(),
                              operations );
         
         if ( ok )
         {
            // Replace the content of the lhs taskseries with the content of the rhs taskseries.
            final ContentValues rhs_Values = RtmTaskSeriesProviderPart.getContentValues( rhs,
                                                                                         listId,
                                                                                         false );
            ok = rhs_Values != null;
            
            if ( ok && rhs_Values.size() > 0 )
            {
               ++result.stats.numUpdates;
               
               operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                             lhs.getId() ) )
                                                       .withValues( rhs_Values )
                                                       .build() );
            }
         }
      }
      else
      {
         Log.e( TAG, "Tried to update RtmTaskSeries with different IDs. lhs = "
            + lhs.getId() + ", rhs = " + rhs.getId() );
      }
      
      return ok;
   }
   


   private static boolean in_SyncRtmTask( ContentProviderClient provider,
                                          RtmTask lhs,
                                          RtmTask rhs,
                                          ArrayList< ContentProviderOperation > operations ) throws RemoteException
   {
      boolean ok = lhs.getId().equals( rhs.getId() );
      
      if ( ok )
      {
         // Replace the content of the lhs task with the content of the rhs task.
         final ContentValues rhs_Values = RtmTasksProviderPart.getContentValues( rhs,
                                                                                 false );
         
         ok = rhs_Values != null;
         
         if ( ok && rhs_Values.size() > 0 )
            operations.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          lhs.getId() ) )
                                                    .withValues( rhs_Values )
                                                    .build() );
      }
      else
      {
         Log.e( TAG, "Tried to update RtmTask with different IDs. lhs = "
            + lhs.getId() + ", rhs = " + rhs.getId() );
      }
      
      return ok;
   }
}

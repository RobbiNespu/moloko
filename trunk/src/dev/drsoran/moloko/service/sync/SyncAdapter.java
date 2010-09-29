/*
Copyright (c) 2010 Ronny R�hricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny R�hricht - implementation
*/

package dev.drsoran.moloko.service.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   public final static class Operation
   {
      public final static int INSERT = 0x1;
      
      public final static int UPDATE = 0x2;
      
      public final static int DELETE = 0x4;
   }
   

   public final static class Direction
   {
      public final static int IN = 0;
      
      public final static int OUT = 1;
   }
   
   private static final String TAG = SyncAdapter.class.getSimpleName();
   
   private final AccountManager accountManager;
   
   private final Context context;
   
   private Date lastUpdated;
   
   private ServiceImpl serviceImpl;
   
   

   public SyncAdapter( Context context, boolean autoInitialize )
   {
      super( context, autoInitialize );
      
      this.context = context;
      this.accountManager = AccountManager.get( context );
   }
   


   @Override
   public void onPerformSync( Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult )
   {
      // TODO: Remove debug wait
      // Debug.waitForDebugger();
      
      String authToken = null;
      
      try
      {
         // use the account manager to request the credentials
         authToken = accountManager.blockingGetAuthToken( account,
                                                          Constants.AUTH_TOKEN_TYPE,
                                                          true /* notifyAuthFailure */);
         
         if ( authToken != null )
         {
            // Check if we have all account info
            final String apiKey = accountManager.getUserData( account,
                                                              Constants.FEAT_API_KEY );
            final String sharedSecret = accountManager.getUserData( account,
                                                                    Constants.FEAT_SHARED_SECRET );
            
            if ( apiKey == null || sharedSecret == null )
            {
               accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                   authToken );
            }
            else
            {
               serviceImpl = new ServiceImpl( new ApplicationInfo( apiKey,
                                                                   sharedSecret,
                                                                   context.getString( R.string.app_name ),
                                                                   authToken ) );
               
               final ArrayList< IContentProviderSyncOperation > batch = new ArrayList< IContentProviderSyncOperation >();
               
               if ( computeOperationsBatch( provider, syncResult, extras, batch ) )
               {
                  final ArrayList< ContentProviderOperation > contentProviderOperationsBatch = new ArrayList< ContentProviderOperation >();
                  
                  for ( IContentProviderSyncOperation contentProviderSyncOperation : batch )
                  {
                     final int count = contentProviderSyncOperation.getBatch( contentProviderOperationsBatch );
                     ContentProviderSyncOperation.updateSyncResult( syncResult,
                                                                    contentProviderSyncOperation.getOperationType(),
                                                                    count );
                  }
                  
                  provider.applyBatch( contentProviderOperationsBatch );
                  
                  lastUpdated = new Date();
                  Log.i( TAG, "Applying sync operations batch succeded." );
               }
               else
               {
                  if ( syncResult.stats.numAuthExceptions > 0 )
                  {
                     Log.e( TAG,
                            syncResult.stats.numAuthExceptions
                               + " authentication exceptions. Invalidating auth token." );
                     
                     accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                         authToken );
                  }
                  
                  Log.e( TAG, "Applying sync operations batch failed." );
                  clearSyncResult( syncResult );
               }
            }
         }
         else
         {
            accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                authToken );
         }
      }
      catch ( final AuthenticatorException e )
      {
         syncResult.stats.numParseExceptions++;
         Log.e( TAG, "AuthenticatorException", e );
      }
      catch ( final OperationCanceledException e )
      {
         Log.e( TAG, "OperationCanceledExcetpion", e );
      }
      catch ( final IOException e )
      {
         Log.e( TAG, "IOException", e );
         syncResult.stats.numIoExceptions++;
      }
      catch ( final ParseException e )
      {
         syncResult.stats.numParseExceptions++;
         Log.e( TAG, "ParseException", e );
      }
      catch ( ServiceInternalException e )
      {
         syncResult.stats.numIoExceptions++;
         Log.e( TAG, "ServiceInternalException", e );
      }
      catch ( RemoteException e )
      {
         syncResult.stats.numIoExceptions++;
         Log.e( TAG, "RemoteException", e );
      }
      catch ( OperationApplicationException e )
      {
         syncResult.stats.numIoExceptions++;
         syncResult.databaseError = true;
         Log.e( TAG, "OperationApplicationException", e );
      }
   }
   


   private boolean computeOperationsBatch( ContentProviderClient provider,
                                           SyncResult syncResult,
                                           Bundle extras,
                                           ArrayList< IContentProviderSyncOperation > batch )
   {
      boolean ok = true;
      
      if ( !extras.getBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                               false ) )
      {
         // Sync RtmList
         ok = RtmListsSync.in_computeSync( provider,
                                           serviceImpl,
                                           syncResult,
                                           batch );
         
         Log.i( TAG, "Compute RtmLists sync " + ( ok ? "ok" : "failed" ) );
         
         // Sync RtmTasks
         ok = ok
            && RtmTasksSync.in_computeSync( provider,
                                            serviceImpl,
                                            syncResult,
                                            batch );
         
         Log.i( TAG, "Compute RtmTasks sync " + ( ok ? "ok" : "failed" ) );
         
         // Sync locations
         ok = ok
            && RtmLocationsSync.in_computeSync( provider,
                                                serviceImpl,
                                                syncResult,
                                                batch );
         
         Log.i( TAG, "Compute RtmLocations sync " + ( ok ? "ok" : "failed" ) );
      }
      
      // Sync settings
      ok = ok
         && RtmSettingsSync.in_computeSync( provider,
                                            serviceImpl,
                                            syncResult,
                                            batch );
      
      Log.i( TAG, "Compute RtmSettings sync " + ( ok ? "ok" : "failed" ) );
      
      if ( !ok )
         batch.clear();
      
      return ok;
   }
   


   @SuppressWarnings( "unused" )
   private boolean checkAuthToken( String authToken ) throws IOException,
                                                     ParseException
   {
      boolean valid = false;
      
      try
      {
         serviceImpl.auth_checkToken( authToken );
         valid = true;
      }
      catch ( ServiceException e )
      {
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.INVALID_AUTH_TOKEN:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               valid = false;
               break;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               throw new IOException( e.responseMessage );
            default :
               throw new ParseException( e.responseMessage );
         }
      }
      
      return valid;
   }
   


   private static void clearSyncResult( SyncResult syncResult )
   {
      syncResult.stats.numInserts = 0;
      syncResult.stats.numUpdates = 0;
      syncResult.stats.numDeletes = 0;
   }
}
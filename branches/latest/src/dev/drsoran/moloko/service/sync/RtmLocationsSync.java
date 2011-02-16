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

package dev.drsoran.moloko.service.sync;

import java.util.Date;
import java.util.List;

import android.content.ContentProviderClient;
import android.content.SyncResult;
import android.util.Log;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmLocation;

import dev.drsoran.moloko.content.RtmLocationsProviderPart;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;
import dev.drsoran.moloko.util.SyncUtils;


public final class RtmLocationsSync
{
   private final static String TAG = "Moloko."
      + RtmLocationsSync.class.getSimpleName();
   
   

   public static boolean computeSync( Service service,
                                      ContentProviderClient provider,
                                      Date lastSyncOut,
                                      SyncResult syncResult,
                                      DirectedSyncOperations operations )
   {
      // Get all locations from local database
      final List< RtmLocation > local_Locations = RtmLocationsProviderPart.getAllLocations( provider );
      
      if ( local_Locations == null )
      {
         syncResult.databaseError = true;
         Log.e( TAG, "Getting local locations failed." );
         return false;
      }
      
      List< RtmLocation > server_Locations = null;
      
      try
      {
         server_Locations = service.locations_getList();
      }
      catch ( ServiceException e )
      {
         Log.e( TAG, "Getting server lists failed.", e );
         
         switch ( e.responseCode )
         {
            case RtmServiceConstants.RtmErrorCodes.LOGIN_FAILED:
            case RtmServiceConstants.RtmErrorCodes.INVALID_API_KEY:
               ++syncResult.stats.numAuthExceptions;
               break;
            case RtmServiceConstants.RtmErrorCodes.SERVICE_UNAVAILABLE:
               ++syncResult.stats.numIoExceptions;
               break;
            default :
               if ( e instanceof ServiceInternalException )
                  SyncUtils.handleServiceInternalException( (ServiceInternalException) e,
                                                            TAG,
                                                            syncResult );
               else
                  ++syncResult.stats.numParseExceptions;
               break;
         }
         
         return false;
      }
      
      // Sync location lists
      final ContentProviderSyncableList< RtmLocation > local_SyncList = new ContentProviderSyncableList< RtmLocation >( local_Locations,
                                                                                                                        RtmLocation.LESS_ID );
      final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( server_Locations,
                                                                                    local_SyncList );
      operations.getLocalOperations().addAll( syncOperations );
      
      return true;
   }
}

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

package dev.drsoran.moloko.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.R;


class PermanentNotifier extends AbstractNotificator
{
   private final PermanentNotificationPresenter presenter;
   
   private int notificationType;
   
   private boolean showOverDueTasks;
   
   private String lastLoaderfilterString;
   
   
   
   public PermanentNotifier( Context context )
   {
      super( context );
      
      presenter = new PermanentNotificationPresenter( context );
      
      readPreferences();
      reEvaluatePermanentNotification();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MIDNIGHT:
         case IOnTimeChangedListener.SYSTEM_TIME:
            reEvaluatePermanentNotification();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.DATEFORMAT:
            reEvaluatePermanentNotification();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void shutdown()
   {
      super.shutdown();
      cancelPermanentNotification();
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      super.onSharedPreferenceChanged( sharedPreferences, key );
      
      if ( sharedPreferences != null && key != null )
      {
         if ( key.equals( context.getString( R.string.key_notify_permanent ) )
            || key.equals( context.getString( R.string.key_notify_permanent_overdue ) ) )
         {
            readPreferences();
            reEvaluatePermanentNotification();
         }
      }
   }
   
   
   
   @Override
   protected void onFinishedLoadingTasksToNotify( Cursor cursor )
   {
      if ( cursor != null && cursor.moveToFirst() )
      {
         buildOrUpdatePermanentNotification( cursor );
      }
      else
      {
         cancelPermanentNotification();
      }
   }
   
   
   
   @Override
   protected void onDatasetChanged()
   {
      reEvaluatePermanentNotification();
   }
   
   
   
   private void reEvaluatePermanentNotification()
   {
      if ( notificationType == R.integer.notification_permanent_off
         && !showOverDueTasks )
      {
         stopLoadingTasksToNotify();
         cancelPermanentNotification();
         
         lastLoaderfilterString = null;
      }
      else
      {
         LoadPermanentTasksAsyncTask loader = new LoadPermanentTasksAsyncTask( context,
                                                                               getHandler(),
                                                                               notificationType,
                                                                               showOverDueTasks );
         lastLoaderfilterString = loader.getFilterString();
         
         startTasksLoader( loader );
      }
   }
   
   
   
   private void cancelPermanentNotification()
   {
      presenter.cancelNotification();
   }
   
   
   
   private void buildOrUpdatePermanentNotification( Cursor cursor )
   {
      presenter.showNotificationFor( cursor, lastLoaderfilterString );
   }
   
   
   
   private void readPreferences()
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         notificationType = Integer.parseInt( prefs.getString( context.getString( R.string.key_notify_permanent ),
                                                               String.valueOf( R.integer.notification_permanent_off ) ) );
         showOverDueTasks = prefs.getBoolean( context.getString( R.string.key_notify_permanent_overdue ),
                                              false );
      }
   }
}
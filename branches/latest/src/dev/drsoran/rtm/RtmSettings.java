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

package dev.drsoran.rtm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.Log;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Settings;


public class RtmSettings extends RtmData implements
         IContentProviderSyncable< RtmSettings >
{
   private final String TAG = "Moloko." + RtmSettings.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmSettings > CREATOR = new Parcelable.Creator< RtmSettings >()
   {
      
      public RtmSettings createFromParcel( Parcel source )
      {
         return new RtmSettings( source );
      }
      


      public RtmSettings[] newArray( int size )
      {
         return new RtmSettings[ size ];
      }
      
   };
   
   private final ParcelableDate syncTimeStamp;
   
   private final String timezone;
   
   private int dateFormat;
   
   private int timeFormat;
   
   private final String defaultListId;
   
   private final String language;
   
   

   public static RtmSettings createDefaultSettings( Context context )
   {
      final String timeZone = Calendar.getInstance().getTimeZone().getID();
      
      final char[] order = DateFormat.getDateFormatOrder( context );
      
      final int dateformat = ( order.length > 0 && order[ 0 ] == DateFormat.DATE )
                                                                                  ? 0
                                                                                  : 1;
      
      final int timeformat = ( !DateFormat.is24HourFormat( context ) ) ? 0 : 1;
      
      final String language = Locale.getDefault().getLanguage();
      
      return new RtmSettings( new Date(),
                              timeZone,
                              dateformat,
                              timeformat,
                              null,
                              language );
   }
   


   public RtmSettings( Date syncTimeStamp, String timezone, int dateFormat,
      int timeFormat, String defaultListId, String language )
   {
      this.syncTimeStamp = ( syncTimeStamp != null )
                                                    ? new ParcelableDate( syncTimeStamp )
                                                    : null;
      this.timezone = timezone;
      this.dateFormat = dateFormat;
      this.timeFormat = timeFormat;
      this.defaultListId = defaultListId;
      this.language = language;
   }
   


   public RtmSettings( Element elt )
   {
      this.syncTimeStamp = new ParcelableDate( new Date() );
      
      if ( !elt.getNodeName().equals( "settings" ) )
      {
         throw new IllegalArgumentException( "Element " + elt.getNodeName()
            + " does not represent an Settings object." );
      }
      
      this.timezone = textNullIfEmpty( child( elt, "timezone" ) );
      
      try
      {
         this.dateFormat = Integer.parseInt( text( child( elt, "dateformat" ) ) );
      }
      catch ( NumberFormatException nfe )
      {
         this.dateFormat = 0;
         Log.e( TAG, "Invalid dateformat setting.", nfe );
      }
      
      try
      {
         this.timeFormat = Integer.parseInt( text( child( elt, "timeformat" ) ) );
      }
      catch ( NumberFormatException nfe )
      {
         this.timeFormat = 0;
         Log.e( TAG, "Invalid timeformat setting.", nfe );
      }
      
      this.defaultListId = textNullIfEmpty( child( elt, "defaultlist" ) );
      this.language = textNullIfEmpty( child( elt, "language" ) );
   }
   


   public RtmSettings( Parcel source )
   {
      this.syncTimeStamp = source.readParcelable( null );
      this.timezone = source.readString();
      this.dateFormat = source.readInt();
      this.timeFormat = source.readInt();
      this.defaultListId = source.readString();
      this.language = source.readString();
   }
   


   public Date getSyncTimeStamp()
   {
      return syncTimeStamp.getDate();
   }
   


   public String getTimezone()
   {
      return timezone;
   }
   


   public int getDateFormat()
   {
      return dateFormat;
   }
   


   public int getTimeFormat()
   {
      return timeFormat;
   }
   


   public String getDefaultListId()
   {
      return defaultListId;
   }
   


   public String getLanguage()
   {
      return language;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeParcelable( syncTimeStamp, flags );
      dest.writeString( timezone );
      dest.writeInt( dateFormat );
      dest.writeInt( timeFormat );
      dest.writeString( defaultListId );
      dest.writeString( language );
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Settings.CONTENT_URI )
                                                                             .withValues( RtmSettingsProviderPart.getContentValues( this ) )
                                                                             .build() )
                                         .build();
   }
   


   public List< IContentProviderSyncOperation > computeContentProviderUpdateOperations( Date lastSync,
                                                                                        RtmSettings update )
   {
      final Uri settingsUri = Queries.contentUriWithId( Settings.CONTENT_URI,
                                                        RtmSettingsProviderPart.SETTINGS_ID );
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      result.add( ContentProviderOperation.newUpdate( settingsUri )
                                          .withValue( Settings.SYNC_TIMESTAMP,
                                                      update.getSyncTimeStamp()
                                                            .getTime() )
                                          .build() );
      
      if ( SyncUtils.hasChanged( timezone, update.timezone ) )
         result.add( ContentProviderOperation.newUpdate( settingsUri )
                                             .withValue( Settings.TIMEZONE,
                                                         update.timezone )
                                             .build() );
      
      if ( update.dateFormat != dateFormat )
         result.add( ContentProviderOperation.newUpdate( settingsUri )
                                             .withValue( Settings.DATEFORMAT,
                                                         update.dateFormat )
                                             .build() );
      
      if ( update.timeFormat != timeFormat )
         result.add( ContentProviderOperation.newUpdate( settingsUri )
                                             .withValue( Settings.TIMEFORMAT,
                                                         update.timeFormat )
                                             .build() );
      
      if ( SyncUtils.hasChanged( defaultListId, update.defaultListId ) )
         result.add( ContentProviderOperation.newUpdate( settingsUri )
                                             .withValue( Settings.DEFAULTLIST_ID,
                                                         update.defaultListId )
                                             .build() );
      
      if ( SyncUtils.hasChanged( language, update.language ) )
         result.add( ContentProviderOperation.newUpdate( settingsUri )
                                             .withValue( Settings.LANGUAGE,
                                                         update.language )
                                             .build() );
      
      return result.asList();
   }
   
}

/* 
 *	Copyright (c) 2011 Ronny R�hricht
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

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;

import com.mdt.rtm.data.RtmList;

import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.ContentProviderAction;
import dev.drsoran.moloko.content.ContentProviderActionItemList;
import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.provider.Rtm.Lists;


public final class RtmListEditUtils
{
   private RtmListEditUtils()
   {
      throw new AssertionError();
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > setListName( FragmentActivity activity,
                                                                                            String listId,
                                                                                            String name )
   {
      final ModificationSet modifications = new ModificationSet();
      
      modifications.add( Modification.newModification( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                 listId ),
                                                       Lists.LIST_NAME,
                                                       name ) );
      modifications.add( Modification.newListModified( listId ) );
      
      return Pair.create( modifications.toContentProviderActionItemList(),
                          new ApplyChangesInfo( activity.getString( R.string.toast_save_list ),
                                                activity.getString( R.string.toast_save_list_ok ),
                                                activity.getString( R.string.toast_save_list_failed ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > deleteListByName( FragmentActivity activity,
                                                                                                 String listName )
   {
      final ContentProviderClient client = activity.getContentResolver()
                                                   .acquireContentProviderClient( Lists.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmList list = RtmListsProviderPart.getListByName( client,
                                                                  listName );
         client.release();
         
         if ( list != null )
            return deleteList( activity, list );
      }
      
      return null;
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > insertList( FragmentActivity activity,
                                                                                           RtmList list )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      
      boolean ok = actionItemList.add( ContentProviderAction.Type.INSERT,
                                       RtmListsProviderPart.insertLocalCreatedList( list ) );
      ok = ok
         && actionItemList.add( ContentProviderAction.Type.INSERT,
                                CreationsProviderPart.newCreation( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                             list.getId() ),
                                                                   list.getCreatedDate()
                                                                       .getTime() ) );
      if ( !ok )
         actionItemList = null;
      
      return Pair.create( actionItemList,
                          new ApplyChangesInfo( activity.getString( R.string.toast_insert_list ),
                                                activity.getString( R.string.toast_insert_list_ok ),
                                                activity.getString( R.string.toast_insert_list_fail ) ) );
   }
   
   
   
   public final static Pair< ContentProviderActionItemList, ApplyChangesInfo > deleteList( FragmentActivity activity,
                                                                                           RtmList list )
   {
      ContentProviderActionItemList actionItemList = new ContentProviderActionItemList();
      ApplyChangesInfo applyChangesInfo;
      
      if ( list.getLocked() == 0 )
      {
         boolean ok = true;
         
         final String listId = list.getId();
         final ModificationSet modifications = new ModificationSet();
         
         modifications.add( Modification.newNonPersistentModification( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                 listId ),
                                                                       Lists.LIST_DELETED,
                                                                       System.currentTimeMillis() ) );
         modifications.add( Modification.newListModified( listId ) );
         
         // Move all contained tasks of the deleted List to the Inbox, this only applies to non-smart lists.
         if ( list.getSmartFilter() == null )
         {
            final ArrayList< ContentProviderOperation > moveTasksToInboxOps = RtmTaskSeriesProviderPart.moveTaskSeriesToInbox( activity.getContentResolver(),
                                                                                                                               listId,
                                                                                                                               activity.getString( R.string.app_list_name_inbox ) );
            ok = moveTasksToInboxOps != null;
            ok = ok
               && actionItemList.addAll( ContentProviderAction.Type.UPDATE,
                                         moveTasksToInboxOps );
         }
         
         ok = ok
            && actionItemList.add( ContentProviderAction.Type.DELETE,
                                   CreationsProviderPart.deleteCreation( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                   listId ) ) );
         
         // Add the modifications to the actionItemList
         // Remove the default list setting, if same list ID
         if ( ok )
         {
            actionItemList.add( 0, modifications );
            
            final String defaultListId = MolokoApp.getSettings()
                                                  .getDefaultListId();
            if ( defaultListId.equals( listId ) )
            {
               MolokoApp.getSettings()
                        .setDefaultListId( Settings.NO_DEFAULT_LIST_ID );
            }
         }
         else
         {
            actionItemList = null;
         }
         
         applyChangesInfo = new ApplyChangesInfo( activity.getString( R.string.toast_delete_list ),
                                                  activity.getString( R.string.toast_delete_list_ok ),
                                                  activity.getString( R.string.toast_delete_list_failed ) );
      }
      else
      {
         applyChangesInfo = new ApplyChangesInfo( activity.getString( R.string.toast_delete_list ),
                                                  activity.getString( R.string.toast_delete_list_ok ),
                                                  activity.getString( R.string.toast_delete_locked_list ) );
      }
      
      return Pair.create( actionItemList, applyChangesInfo );
   }
}
/* 
 *	Copyright (c) 2013 Ronny R�hricht
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

package dev.drsoran.moloko.content.db;

import android.content.ContentValues;
import android.database.Cursor;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.rtm.content.Compare;


public class DbModificationsApplier implements IModificationsApplier
{
   private final static String SEL_QUERY_MODIFICATION = new StringBuilder( 100 ).append( ModificationColumns.ENTITY_URI )
                                                                                .append( "=? AND " )
                                                                                .append( ModificationColumns.PROPERTY )
                                                                                .append( "=?" )
                                                                                .toString();
   
   private final ITable modificationsTable;
   
   private final IContentValuesFactory contentValuesFactory;
   
   
   
   public DbModificationsApplier( ITable modificationsTable,
      IContentValuesFactory contentValuesFactory )
   {
      this.modificationsTable = modificationsTable;
      this.contentValuesFactory = contentValuesFactory;
   }
   
   
   
   @Override
   public void applyPersistentModifications( Iterable< Modification > modifications ) throws ContentException
   {
      Cursor c = null;
      try
      {
         for ( Modification modification : modifications )
         {
            if ( modification.isPersistent() )
            {
               // Check if modification already exists
               c = getModification( modification.getEntityUri(),
                                    modification.getPropertyName() );
               
               if ( c.moveToNext() )
               {
                  updateOrRevertExistingModification( c, modification );
               }
               else
               {
                  insertNewModification( modification );
               }
               
               c.close();
               c = null;
            }
         }
      }
      catch ( Throwable e )
      {
         throw new ContentException( "Failed to apply modifications", e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private Cursor getModification( String entityUri, String columnName )
   {
      return modificationsTable.query( ModificationColumns.PROJECTION,
                                       SEL_QUERY_MODIFICATION,
                                       new String[]
                                       { entityUri, columnName },
                                       null );
   }
   
   
   
   private void insertNewModification( Modification modification )
   {
      modificationsTable.insert( contentValuesFactory.createContentValues( modification ) );
   }
   
   
   
   private void updateOrRevertExistingModification( Cursor existingModification,
                                                    Modification newModification )
   {
      // Check if the new value equals the synced value from the existing modification, if so the
      // user has reverted his change and we delete the modification.
      if ( Compare.isDifferent( CursorUtils.getOptString( existingModification,
                                                          ModificationColumns.SYNCED_VALUE_IDX ),
                                newModification.getValue() ) )
      {
         // Update the modification with the new value.
         modificationsTable.update( existingModification.getLong( Columns.ID_IDX ),
                                    createUpdateNewValueContentValues( newModification.getValue() ),
                                    null,
                                    null );
      }
      else
      {
         modificationsTable.delete( existingModification.getLong( Columns.ID_IDX ),
                                    null,
                                    null );
      }
   }
   
   
   
   private static ContentValues createUpdateNewValueContentValues( String newValue )
   {
      final ContentValues values = new ContentValues( 1 );
      values.put( ModificationColumns.NEW_VALUE, newValue );
      
      return values;
   }
}

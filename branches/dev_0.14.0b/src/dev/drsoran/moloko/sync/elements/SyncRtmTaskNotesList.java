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

package dev.drsoran.moloko.sync.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.mdt.rtm.data.RtmTaskNote;


public class SyncRtmTaskNotesList
{
   private interface IGetModeAction
   {
      < T > void perform( List< T > list, Comparator< ? super T > cmp );
   }
   

   private static enum GetMode implements IGetModeAction
   {
      AS_IS
      {
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            
         }
      },
      SORTED
      {
         public < T > void perform( List< T > list, Comparator< ? super T > cmp )
         {
            Collections.sort( list, cmp );
         }
      }
      
   }
   
   private static final Comparator< SyncNote > LESS_ID = new Comparator< SyncNote >()
   {
      public int compare( SyncNote object1, SyncNote object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   };
   
   private final List< SyncNote > notes;
   
   

   public SyncRtmTaskNotesList()
   {
      notes = new ArrayList< SyncNote >();
   }
   


   public SyncRtmTaskNotesList( List< RtmTaskNote > notes )
   {
      if ( notes == null )
         throw new NullPointerException( "notes is null" );
      
      this.notes = new ArrayList< SyncNote >( notes.size() );
      
      for ( RtmTaskNote rtmTaskNote : notes )
         this.notes.add( new SyncNote( null, rtmTaskNote ) );
      
      Collections.sort( this.notes, LESS_ID );
   }
   


   public List< SyncNote > getSyncNotes()
   {
      return getSyncNotes( SyncNote.LESS_ID );
   }
   


   public List< SyncNote > getSyncNotes( Comparator< ? super SyncNote > cmp )
   {
      final List< SyncNote > res = new ArrayList< SyncNote >( notes );
      
      if ( cmp != null )
         GetMode.SORTED.perform( res, cmp );
      
      return res;
   }
   


   public void add( SyncNote note )
   {
      update( note );
   }
   


   public void remove( SyncNote note )
   {
      final int pos = Collections.binarySearch( notes, note, LESS_ID );
      
      if ( pos >= 0 )
         notes.remove( pos );
   }
   


   public void intersect( List< RtmTaskNote > notes )
   {
      for ( Iterator< SyncNote > i = this.notes.iterator(); i.hasNext(); )
      {
         SyncNote syncNote = i.next();
         
         boolean found = false;
         
         for ( RtmTaskNote rtmTaskNote : notes )
         {
            if ( rtmTaskNote.getId().equals( syncNote.getId() ) )
            {
               found = true;
               break;
            }
         }
         
         if ( !found )
            i.remove();
      }
   }
   


   public SyncNote get( int location )
   {
      return notes.get( location );
   }
   


   public int size()
   {
      return notes.size();
   }
   


   public void update( SyncNote note )
   {
      if ( note == null )
         throw new NullPointerException( "note is null" );
      
      // If the set already contains an element in respect to the Comparator,
      // then we update it by the new.
      final int pos = Collections.binarySearch( notes, note, LESS_ID );
      
      if ( pos >= 0 )
      {
         notes.remove( pos );
         notes.add( pos, note );
      }
      else
      {
         notes.add( ( -pos - 1 ), note );
      }
   }
   


   @Override
   public String toString()
   {
      return "<" + notes.size() + ">";
   }
}
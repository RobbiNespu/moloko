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

package dev.drsoran.rtm.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;


public class DeletedRtmTaskSeriesContentHandler extends
         RtmNestedContentHandler< Collection< RtmTask > >
{
   private final String listId;
   
   private final Collection< RtmTask > tasks = new ArrayList< RtmTask >( 1 );
   
   private Attributes taskSeriesAttributes;
   
   
   
   public DeletedRtmTaskSeriesContentHandler( String listId )
   {
      this( listId, null );
   }
   
   
   
   public DeletedRtmTaskSeriesContentHandler( String listId,
      IRtmContentHandlerListener< Collection< RtmTask >> listener )
   {
      super( listener );
      this.listId = listId;
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         taskSeriesAttributes = XmlAttr.copy( attributes );
      }
      else if ( "task".equalsIgnoreCase( qName ) )
      {
         addTask( attributes );
      }
      else
      {
         super.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "taskseries".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( tasks );
         taskSeriesAttributes = null;
      }
      else
      {
         super.endElement( uri, localName, qName );
      }
   }
   
   
   
   private void addTask( Attributes taskAttributes ) throws SAXException
   {
      final long nowMillis = System.currentTimeMillis();
      
      final RtmTask task = new RtmTask( XmlAttr.getStringNotNull( taskAttributes,
                                                                  "id" ),
                                        XmlAttr.getStringNotNull( taskSeriesAttributes,
                                                                  "id" ),
                                        nowMillis,
                                        nowMillis,
                                        nowMillis,
                                        XmlAttr.getOptMillisUtc( taskAttributes,
                                                                 "deleted" ),
                                        listId,
                                        RtmConstants.NO_ID,
                                        "Deleted Task",
                                        Strings.EMPTY_STRING,
                                        Strings.EMPTY_STRING,
                                        RtmConstants.NO_TIME,
                                        Priority.None,
                                        0,
                                        RtmConstants.NO_TIME,
                                        false,
                                        null,
                                        false,
                                        null,
                                        Collections.< String > emptyList(),
                                        Collections.< RtmNote > emptyList(),
                                        Collections.< RtmContact > emptyList() );
      tasks.add( task );
   }
}

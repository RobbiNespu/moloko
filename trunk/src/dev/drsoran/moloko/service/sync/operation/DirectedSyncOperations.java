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

package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class DirectedSyncOperations
{
   private final List< ISyncOperation > serverOperations;
   
   private final List< IContentProviderSyncOperation > localOperations;
   
   

   public DirectedSyncOperations( List< ISyncOperation > serverOps,
      List< IContentProviderSyncOperation > localOps )
   {
      serverOperations = Collections.unmodifiableList( serverOps );
      localOperations = Collections.unmodifiableList( localOps );
   }
   


   public DirectedSyncOperations()
   {
      serverOperations = new ArrayList< ISyncOperation >();
      localOperations = new ArrayList< IContentProviderSyncOperation >();
   }
   


   public void addAll( DirectedSyncOperations other )
   {
      serverOperations.addAll( other.localOperations );
      localOperations.addAll( other.localOperations );
   }
   


   public void clear()
   {
      serverOperations.clear();
      localOperations.clear();
   }
   


   public List< ISyncOperation > getServerOperations()
   {
      return serverOperations;
   }
   


   public List< IContentProviderSyncOperation > getLocalOperations()
   {
      return localOperations;
   }
}
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

package dev.drsoran.moloko.fragments.base;

import android.support.v4.app.SupportActivity;
import android.util.Pair;
import dev.drsoran.moloko.ApplyChangesInfo;
import dev.drsoran.moloko.IEditFragment;
import dev.drsoran.moloko.content.ContentProviderActionItemList;


public abstract class MolokoLoaderEditFragment< T, D > extends
         MolokoLoaderFragment< D > implements IEditFragment< T >
{
   private final EditFragmentImpl impl;
   
   
   
   protected MolokoLoaderEditFragment()
   {
      impl = new EditFragmentImpl( this );
   }
   
   
   
   @Override
   public void onAttach( SupportActivity activity )
   {
      super.onAttach( activity );
      impl.onAttach( activity.asActivity() );
   }
   
   
   
   @Override
   public void onDetach()
   {
      impl.onDetach();
      super.onDetach();
   }
   
   
   
   @Override
   public void onDestroyView()
   {
      impl.onDestroyView();
      super.onDestroyView();
   }
   
   
   
   @Override
   public final boolean onFinishEditing()
   {
      boolean ok = true;
      
      if ( hasChanges() )
      {
         ok = saveChanges();
      }
      
      return ok;
   }
   
   
   
   @Override
   public void onCancelEditing()
   {
   }
   
   
   
   protected boolean applyModifications( ContentProviderActionItemList actionItemList,
                                         ApplyChangesInfo applyChangesInfo )
   {
      return impl.applyModifications( actionItemList, applyChangesInfo );
   }
   
   
   
   protected boolean applyModifications( Pair< ContentProviderActionItemList, ApplyChangesInfo > modifications )
   {
      return impl.applyModifications( modifications.first, modifications.second );
   }
   
   
   
   protected abstract boolean saveChanges();
}

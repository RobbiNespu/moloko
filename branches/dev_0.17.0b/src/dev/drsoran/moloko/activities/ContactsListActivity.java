/*
 * Copyright (c) 2012 Ronny R�hricht
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

package dev.drsoran.moloko.activities;

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.fragments.listeners.IContactsListFragmentListener;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MenuCategory;
import dev.drsoran.moloko.util.MolokoMenuItemBuilder;


public class ContactsListActivity extends MolokoFragmentActivity implements
         IContactsListFragmentListener
{
   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      
      setContentView( R.layout.contactslist_activity );
   }
   
   
   
   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      MolokoMenuItemBuilder.newSettingsMenuItem( this )
                           .setOrder( MenuCategory.ALTERNATIVE )
                           .build( menu );
      
      MolokoMenuItemBuilder.newSyncMenuItem( this )
                           .setShowAsActionFlags( MenuItem.SHOW_AS_ACTION_IF_ROOM )
                           .build( menu );
      
      return true;
   }
   
   
   
   @Override
   public void onShowPhoneBookEntryOfContact( String lookUpKey )
   {
      startActivity( Intents.createShowPhonebookContactIntent( lookUpKey ) );
   }
   
   
   
   @Override
   public void onShowTasksOfContact( String fullname, String username )
   {
      startActivity( Intents.createOpenContactIntent( this, fullname, username ) );
   }
   
   
   
   @Override
   protected int[] getFragmentIds()
   {
      return new int[]
      { R.id.frag_contactslist };
   }
}

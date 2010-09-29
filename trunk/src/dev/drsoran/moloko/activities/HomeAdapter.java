/* 
	Copyright (c) 2010 Ronny R�hricht

	This file is part of Moloko.

	Moloko is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	Moloko is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

	Contributors:
     Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.layouts.SimpleHomeWidgetLayout;
import dev.drsoran.moloko.widgets.CalendarHomeWidget;
import dev.drsoran.moloko.widgets.OverDueTasksHomeWidget;
import dev.drsoran.provider.Rtm.ListOverviews;


public class HomeAdapter extends BaseAdapter
{
   private final View[] WIDGETS;
   
   

   public HomeAdapter( Context context )
   {
      WIDGETS = new View[]
      {
       new CalendarHomeWidget( context,
                               null,
                               R.string.home_label_today,
                               CalendarHomeWidget.TODAY ),
       new CalendarHomeWidget( context,
                               null,
                               R.string.home_label_tomorrow,
                               CalendarHomeWidget.TOMORROW ),
       new OverDueTasksHomeWidget( context, null, R.string.home_label_overdue ),
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_tasklists,
                                   R.drawable.lists_black,
                                   new Intent( Intent.ACTION_VIEW,
                                               ListOverviews.CONTENT_URI ) ),
       new SimpleHomeWidgetLayout( context,
                                   null,
                                   R.string.app_tagcloud,
                                   R.drawable.tag_black,
                                   new Intent( context, TagCloudActivity.class ) ) };
   }
   


   public int getCount()
   {
      return WIDGETS.length;
   }
   


   public Object getItem( int position )
   {
      return null;
   }
   


   public long getItemId( int position )
   {
      return 0;
   }
   


   public View getView( int position, View convertView, ViewGroup parent )
   {
      if ( convertView == null )
      {
         return WIDGETS[ position ];
      }
      else
      {
         return convertView;
      }
   }
}
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

package dev.drsoran.moloko.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.activities.TasksListActivity;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.rtm.RtmListWithTaskCount;


public class TaskListsAdapter extends BaseExpandableListAdapter
{
   private final static String TAG = "Moloko."
      + TaskListsAdapter.class.getName();
   
   
   public interface IOnGroupIndicatorClickedListener
   {
      void onGroupIndicatorClicked( View groupView );
   }
   
   private final Context context;
   
   public final static int DUE_TODAY_TASK_COUNT = 1;
   
   public final static int DUE_TOMORROW_TASK_COUNT = 2;
   
   public final static int OVER_DUE_TASK_COUNT = 3;
   
   public final static int COMPLETED_TASK_COUNT = 4;
   
   public final static int SUM_ESTIMATE = 5;
   
   private final static int ID_ICON_DEFAULT_LIST = 1;
   
   private final static int ID_ICON_LOCKED = 2;
   
   private final View.OnClickListener iconExpandCollapseListener = new View.OnClickListener()
   {
      @Override
      public void onClick( View view )
      {
         if ( groupIndicatorClickedListener != null )
            groupIndicatorClickedListener.onGroupIndicatorClicked( view );
      }
   };
   
   private final int groupId;
   
   private final int childId;
   
   private final LayoutInflater inflater;
   
   private final ArrayList< RtmListWithTaskCount > lists;
   
   private IOnGroupIndicatorClickedListener groupIndicatorClickedListener;
   
   
   
   public TaskListsAdapter( Context context, int groupId, int childId,
      List< RtmListWithTaskCount > lists )
   {
      super();
      
      if ( lists == null )
         throw new NullPointerException( "lists must not be null" );
      
      this.context = context;
      this.groupId = groupId;
      this.childId = childId;
      this.inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      this.lists = new ArrayList< RtmListWithTaskCount >( lists );
   }
   
   
   
   public void setOnGroupIndicatorClickedListener( IOnGroupIndicatorClickedListener listener )
   {
      groupIndicatorClickedListener = listener;
   }
   
   
   
   @Override
   public Object getChild( int groupPosition, int childPosition )
   {
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).dueTodayTaskCount );
         case DUE_TOMORROW_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).dueTomorrowTaskCount );
         case OVER_DUE_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).overDueTaskCount );
         case COMPLETED_TASK_COUNT:
            return Integer.valueOf( lists.get( groupPosition )
                                         .getExtendedListInfo( context ).completedTaskCount );
         case SUM_ESTIMATE:
            return MolokoDateUtils.formatEstimated( context,
                                                    lists.get( groupPosition )
                                                         .getExtendedListInfo( context ).sumEstimated );
         default :
            return null;
      }
   }
   
   
   
   public Intent getChildIntent( int groupPosition, int childPosition )
   {
      final RtmListWithTaskCount list = lists.get( groupPosition );
      final String listName = list.getName();
      
      Intent intent = null;
      
      switch ( childPosition + 1 )
      {
         case DUE_TODAY_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            
            intent.removeExtra( TasksListActivity.Config.TITLE );
            intent.putExtra( TasksListActivity.Config.TITLE,
                             context.getString( R.string.taskslist_titlebar_due_today,
                                                listName ) );
            break;
         
         case DUE_TOMORROW_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_LIT
                                                      + DateParser.tokenNames[ DateParser.TOMORROW ] );
            intent.removeExtra( TasksListActivity.Config.TITLE );
            intent.putExtra( TasksListActivity.Config.TITLE,
                             context.getString( R.string.taskslist_titlebar_due_tomorrow,
                                                listName ) );
            break;
         
         case OVER_DUE_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_DUE_BEFORE_LIT
                                                      + DateParser.tokenNames[ DateParser.TODAY ] );
            intent.removeExtra( TasksListActivity.Config.TITLE );
            intent.putExtra( TasksListActivity.Config.TITLE,
                             context.getString( R.string.taskslist_titlebar_overdue,
                                                listName ) );
            break;
         
         case COMPLETED_TASK_COUNT:
            intent = Intents.createOpenListIntent( context,
                                                   list,
                                                   RtmSmartFilterLexer.OP_STATUS_LIT
                                                      + RtmSmartFilterLexer.COMPLETED_LIT );
            intent.removeExtra( TasksListActivity.Config.TITLE );
            intent.putExtra( TasksListActivity.Config.TITLE,
                             context.getString( R.string.taskslist_titlebar_completed,
                                                listName ) );
            break;
         
         default :
            break;
      }
      
      if ( intent != null )
         // We have to remove the list name cause we want prevent the list navigation mode
         intent.removeExtra( TasksListActivity.Config.LIST_NAME );
      
      return intent;
   }
   
   
   
   @Override
   public long getChildId( int groupPosition, int childPosition )
   {
      return childPosition + 1;
   }
   
   
   
   @Override
   public View getChildView( int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent )
   {
      View view;
      
      if ( convertView != null && convertView.getId() == R.id.tasklists_child )
      {
         view = convertView;
      }
      else
      {
         view = inflater.inflate( childId, null, false );
      }
      
      if ( view != null )
      {
         final TextView textView = (TextView) view;
         final Object childObject = getChild( groupPosition, childPosition );
         final int quantity = childObject instanceof Integer
                                                            ? ( (Integer) getChild( groupPosition,
                                                                                    childPosition ) ).intValue()
                                                            : -1;
         
         switch ( childPosition + 1 )
         {
            case DUE_TODAY_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_due_today,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case DUE_TOMORROW_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_due_tomorrow,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case OVER_DUE_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_overdue,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case COMPLETED_TASK_COUNT:
               textView.setText( context.getResources()
                                        .getQuantityString( R.plurals.tasklists_child_completed,
                                                            quantity,
                                                            quantity ) );
               break;
            
            case SUM_ESTIMATE:
               textView.setText( context.getString( R.string.task_datetime_estimate_inline,
                                                    getChild( groupPosition,
                                                              childPosition ) ) );
               break;
            
            default :
               break;
         }
         
      }
      
      return view;
   }
   
   
   
   @Override
   public int getChildrenCount( int groupPosition )
   {
      return SUM_ESTIMATE;
   }
   
   
   
   @Override
   public Object getGroup( int groupPosition )
   {
      return lists.get( groupPosition );
   }
   
   
   
   @Override
   public int getGroupCount()
   {
      return lists.size();
   }
   
   
   
   @Override
   public long getGroupId( int groupPosition )
   {
      return Long.valueOf( lists.get( groupPosition ).getId() );
   }
   
   
   
   @Override
   public View getGroupView( int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent )
   {
      View view;
      
      if ( convertView != null && convertView.getId() == R.id.tasklists_group )
      {
         view = convertView;
      }
      else
      {
         view = inflater.inflate( groupId, null, false );
      }
      
      if ( view != null )
      {
         ImageView groupIndicator;
         TextView listName;
         TextView tasksCount;
         ViewGroup iconsContainer;
         
         try
         {
            groupIndicator = (ImageView) view.findViewById( R.id.tasklists_group_indicator );
            groupIndicator.setOnClickListener( iconExpandCollapseListener );
            
            listName = (TextView) view.findViewById( R.id.tasklists_group_list_name );
            tasksCount = (TextView) view.findViewById( R.id.tasklists_group_num_tasks );
            iconsContainer = (ViewGroup) view.findViewById( R.id.tasklists_group_icons_container );
         }
         catch ( ClassCastException e )
         {
            Log.e( TAG, "Invalid layout spec.", e );
            throw e;
         }
         
         if ( isExpanded )
         {
            groupIndicator.setImageResource( R.drawable.expander_ic_maximized );
         }
         else
         {
            groupIndicator.setImageResource( R.drawable.expander_ic_minimized );
         }
         
         final RtmListWithTaskCount rtmList = lists.get( groupPosition );
         
         final String listNameStr = rtmList.getName();
         listName.setText( listNameStr );
         
         UIUtils.setListTasksCountView( tasksCount, rtmList );
         
         addConditionalIcon( iconsContainer,
                             R.drawable.ic_list_tasklists_flag,
                             ID_ICON_DEFAULT_LIST,
                             rtmList.getId()
                                    .equals( MolokoApp.getSettings()
                                                      .getDefaultListId() ) );
         addConditionalIcon( iconsContainer,
                             R.drawable.ic_list_tasklists_lock,
                             ID_ICON_LOCKED,
                             rtmList.getLocked() != 0 );
      }
      
      return view;
   }
   
   
   
   @Override
   public boolean hasStableIds()
   {
      return true;
   }
   
   
   
   @Override
   public boolean isChildSelectable( int groupPosition, int childPosition )
   {
      return childPosition != SUM_ESTIMATE;
   }
   
   
   
   private void addConditionalIcon( ViewGroup container,
                                    int resId,
                                    int iconId,
                                    boolean condition )
   {
      ImageView icon = (ImageView) container.findViewById( iconId );
      
      // if not already added
      if ( condition && icon == null )
      {
         container.setVisibility( View.VISIBLE );
         
         icon = (ImageView) ImageView.inflate( context,
                                               R.layout.tasklists_fragment_group_icon,
                                               null );
         
         if ( icon != null )
         {
            icon.setId( iconId );
            icon.setImageResource( resId );
            container.addView( icon );
         }
      }
      else if ( !condition && icon != null )
      {
         container.removeView( icon );
         
         if ( container.getChildCount() == 0 )
            container.setVisibility( View.GONE );
      }
   }
}

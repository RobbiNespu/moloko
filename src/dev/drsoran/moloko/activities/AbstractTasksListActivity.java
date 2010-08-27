package dev.drsoran.moloko.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.provider.Rtm.Tasks;


public abstract class AbstractTasksListActivity extends ListActivity implements
         OnClickListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = AbstractTasksListActivity.class.getSimpleName();
   
   public static final String TITLE = "title";
   
   public static final String FILTER = "filter";
   
   public static final String FILTER_EVALUATED = "filter_eval";
   
   public static final String ADAPTER_CONFIG = "adapter_config";
   
   /**
    * If we have a concrete list name, then we do not need to click it. Otherwise we would call the same list again. But
    * this only applies to non smart lists
    */
   public static final String HIDE_LIST_NAME = "hide_list_name";
   
   /**
    * If a tag has been clicked then it makes no sense to show it in the result again.
    */
   public static final String HIDE_TAG_EQUALS = "hide_tag_equals";
   
   protected final Bundle configuration = new Bundle();
   
   

   @Override
   protected void onResume()
   {
      super.onResume();
      refresh();
   }
   


   @Override
   protected void onSaveInstanceState( Bundle outState )
   {
      super.onSaveInstanceState( outState );
      outState.putAll( configuration );
   }
   


   @Override
   protected void onRestoreInstanceState( Bundle state )
   {
      super.onRestoreInstanceState( state );
      
      if ( state != null )
      {
         configuration.clear();
         configuration.putAll( state );
         
         refresh();
      }
   }
   


   public void onClick( View v )
   {
      switch ( v.getId() )
      {
         case R.id.taskslist_listitem_btn_list_name:
            onListNameClicked( v );
            break;
         case R.id.taskslist_listitem_btn_tag:
            onTagClicked( v );
            break;
         default :
            break;
      }
   }
   


   protected void onListNameClicked( View view )
   {
      final TextView listNameCtrl = (TextView) view;
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_LIST_LIT
         + listNameCtrl.getText() );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar,
                                         listNameCtrl.getText() ) );
      
      final Bundle config = new Bundle();
      config.putBoolean( HIDE_LIST_NAME, true );
      
      intent.putExtra( ADAPTER_CONFIG, config );
      
      startActivity( intent );
   }
   


   protected void onTagClicked( View view )
   {
      final TextView tagCtrl = (TextView) view;
      
      final Intent intent = new Intent( Intent.ACTION_VIEW, Tasks.CONTENT_URI );
      intent.putExtra( FILTER, RtmSmartFilterLexer.OP_TAG_LIT
         + tagCtrl.getText() );
      intent.putExtra( TITLE, getString( R.string.taskslist_titlebar_tag,
                                         tagCtrl.getText() ) );
      
      final Bundle config = new Bundle();
      config.putString( HIDE_TAG_EQUALS, tagCtrl.getText().toString() );
      
      intent.putExtra( ADAPTER_CONFIG, config );
      
      startActivity( intent );
   }
   


   abstract protected void refresh();
}

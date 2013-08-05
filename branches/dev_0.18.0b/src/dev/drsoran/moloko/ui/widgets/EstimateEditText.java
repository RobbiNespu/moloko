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

package dev.drsoran.moloko.ui.widgets;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.ui.IChangesTarget;
import dev.drsoran.moloko.ui.UiUtils;
import dev.drsoran.moloko.ui.ValidationResult;


public class EstimateEditText extends ClearableEditText
{
   public final static String EDIT_ESTIMATE_TEXT = "edit_estimate_text";
   
   private Long estimateMillis;
   
   private IChangesTarget changes;
   
   
   
   public EstimateEditText( Context context )
   {
      this( context, null, android.R.attr.editTextStyle );
   }
   
   
   
   public EstimateEditText( Context context, AttributeSet attrs )
   {
      this( context, attrs, android.R.attr.editTextStyle );
   }
   
   
   
   public EstimateEditText( Context context, AttributeSet attrs, int defStyle )
   {
      super( context, attrs, defStyle );
   }
   
   
   
   public void setEstimate( long estimateMillis )
   {
      setEstimateByMillis( estimateMillis );
      updateEditText();
   }
   
   
   
   public void setEstimate( String estimate )
   {
      commitInput( estimate );
   }
   
   
   
   public void putInitialValue( Bundle initialValues )
   {
      initialValues.putString( EDIT_ESTIMATE_TEXT, getTextTrimmed() );
   }
   
   
   
   public void setChangesTarget( IChangesTarget changes )
   {
      this.changes = changes;
   }
   
   
   
   public ValidationResult validate()
   {
      if ( isEnabled() )
      {
         final Long res = parseEstimate( getTextTrimmed() );
         return validateEstimate( res );
      }
      else
      {
         return ValidationResult.OK;
      }
   }
   
   
   
   public Long getEstimateMillis()
   {
      commitInput( getTextTrimmed() );
      return estimateMillis;
   }
   
   
   
   @Override
   public void onEditorAction( int actionCode )
   {
      boolean stayInEditText = false;
      
      if ( UiUtils.hasInputCommitted( actionCode ) )
      {
         setEstimateByString( getTextTrimmed() );
         
         final boolean inputValid = validateEstimate( estimateMillis ).isOk();
         stayInEditText = !inputValid;
         
         if ( inputValid )
            updateEditText();
      }
      
      if ( !stayInEditText )
         super.onEditorAction( actionCode );
   }
   
   
   
   @Override
   protected void onTextChanged( CharSequence text,
                                 int start,
                                 int before,
                                 int after )
   {
      super.onTextChanged( text, start, before, after );
      
      putTextChange();
      
      if ( TextUtils.isEmpty( text ) )
      {
         estimateMillis = handleEmptyInputString();
      }
   }
   
   
   
   private void updateEditText()
   {
      if ( isEstimateSet() )
      {
         if ( estimateMillis.longValue() == Constants.NO_TIME )
         {
            setText( null );
         }
         else
         {
            setText( getUiContext().getDateFormatter()
                                   .formatEstimated( estimateMillis.longValue() ) );
         }
      }
   }
   
   
   
   private Long parseEstimate( String estimateString )
   {
      if ( TextUtils.isEmpty( estimateString ) )
      {
         return handleEmptyInputString();
      }
      else
      {
         try
         {
            return getUiContext().getParsingService()
                                 .getDateTimeParsing()
                                 .parseEstimated( estimateString );
         }
         catch ( GrammarException e )
         {
            return null;
         }
      }
   }
   
   
   
   private Long handleEmptyInputString()
   {
      return Long.valueOf( -1 );
   }
   
   
   
   private void setEstimateByMillis( long estimateMillis )
   {
      this.estimateMillis = estimateMillis;
   }
   
   
   
   private void setEstimateByString( String estimateString )
   {
      estimateMillis = parseEstimate( estimateString );
   }
   
   
   
   private boolean isEstimateSet()
   {
      return estimateMillis != null;
   }
   
   
   
   private ValidationResult validateEstimate( Long estimate )
   {
      final boolean valid = estimate != null;
      if ( !valid )
      {
         return new ValidationResult( getContext().getString( R.string.task_edit_validate_estimate,
                                                              getTextTrimmed() ),
                                      this );
      }
      
      return ValidationResult.OK;
   }
   
   
   
   private void putTextChange()
   {
      if ( changes != null )
      {
         changes.putChange( EDIT_ESTIMATE_TEXT, getTextTrimmed(), String.class );
      }
   }
   
   
   
   private void commitInput( String input )
   {
      if ( isEnabled() )
      {
         setEstimateByString( input );
         updateEditText();
      }
   }
}

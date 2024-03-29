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

package dev.drsoran.moloko.util.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.grammar.RtmSmartFilterLexer;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.RtmSmartFilter;


public final class RtmSmartFilterParsing
{
   private final static RtmSmartFilterLexer rtmSmartFilterLexer = new RtmSmartFilterLexer();
   
   
   public final static class RtmSmartFilterReturn
   {
      public final String queryString;
      
      public final boolean hasCompletedOperator;
      
      public final boolean matchQuery;
      
      
      
      public RtmSmartFilterReturn( String queryString,
         boolean hasCompletedOperator )
      {
         this( queryString, hasCompletedOperator, false );
      }
      
      
      
      public RtmSmartFilterReturn( String queryString,
         boolean hasCompletedOperator, boolean matchQuery )
      {
         this.queryString = queryString;
         this.hasCompletedOperator = hasCompletedOperator;
         this.matchQuery = matchQuery;
      }
      
      
      
      @Override
      public boolean equals( Object o )
      {
         if ( o == this )
         {
            return true;
         }
         
         if ( o == null || o.getClass() != getClass() )
         {
            return false;
         }
         
         final RtmSmartFilterReturn other = (RtmSmartFilterReturn) o;
         
         return other.queryString.equals( queryString )
            && other.hasCompletedOperator == hasCompletedOperator;
      }
      
      
      
      @Override
      public int hashCode()
      {
         int code = queryString.hashCode();
         code = code * 31 ^ ( hasCompletedOperator ? 0 : 1 );
         
         return code;
      }
      
      
      
      @Override
      public String toString()
      {
         return String.format( "%s, complOp: %b",
                               queryString,
                               hasCompletedOperator );
      }
   }
   
   
   
   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( RtmSmartFilter filter,
                                                                                 ArrayList< RtmSmartFilterToken > tokens )
   {
      return evaluateRtmSmartFilter( filter.getFilterString(), tokens );
   }
   
   
   
   public synchronized final static RtmSmartFilterReturn evaluateRtmSmartFilter( String filterString,
                                                                                 ArrayList< RtmSmartFilterToken > tokens )
   {
      final ANTLRNoCaseStringStream input = new ANTLRNoCaseStringStream( filterString );
      
      rtmSmartFilterLexer.setCharStream( input );
      
      try
      {
         final String res = rtmSmartFilterLexer.getResult( tokens );
         return new RtmSmartFilterReturn( res,
                                          rtmSmartFilterLexer.hasStatusCompletedOperator() );
      }
      catch ( RecognitionException e )
      {
         MolokoApp.Log.w( RtmSmartFilterParsing.class, "Failed to lex "
            + filterString, e );
         return null;
      }
   }
   
   
   
   public final static boolean hasOperator( List< RtmSmartFilterToken > tokens,
                                            int operator,
                                            boolean negated )
   {
      for ( RtmSmartFilterToken token : removeAmbiguousTokens( tokens ) )
      {
         if ( token.operatorType == operator && token.isNegated == negated )
            return true;
      }
      
      return false;
   }
   
   
   
   public final static boolean hasOperatorAndValue( List< RtmSmartFilterToken > tokens,
                                                    int operator,
                                                    String value,
                                                    boolean negated )
   {
      for ( RtmSmartFilterToken token : removeAmbiguousTokens( tokens ) )
      {
         if ( token.operatorType == operator
            && token.value.equalsIgnoreCase( value )
            && negated == token.isNegated )
            return true;
      }
      
      return false;
   }
   
   
   
   public final static boolean hasCompletedOperator( List< RtmSmartFilterToken > tokens )
   {
      for ( RtmSmartFilterToken token : tokens )
      {
         final int tokenType = token.operatorType;
         if ( ( tokenType == RtmSmartFilterLexer.OP_STATUS && token.value.equalsIgnoreCase( RtmSmartFilterLexer.COMPLETED_LIT ) )
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_AFTER
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_BEFORE
            || tokenType == RtmSmartFilterLexer.OP_COMPLETED_WITHIN )
            return true;
      }
      
      return false;
   }
   
   
   
   /**
    * Ambiguous is determined by the number a token exists in the list of given tokens. This is the simplest approach to
    * avoid situation with complex logical expressions where a token is entered negated and not negated.
    * 
    * TODO: Use more sophisticated approach like BDDs
    */
   public final static List< RtmSmartFilterToken > removeAmbiguousTokens( List< RtmSmartFilterToken > tokens )
   {
      final List< RtmSmartFilterToken > filterTokens = new LinkedList< RtmSmartFilterToken >( tokens );
      
      Collections.sort( filterTokens, new Comparator< RtmSmartFilterToken >()
      {
         @Override
         public int compare( RtmSmartFilterToken object1,
                             RtmSmartFilterToken object2 )
         {
            return object1.operatorType - object2.operatorType;
         }
      } );
      
      for ( int i = 0, cnt = filterTokens.size(); i < cnt; )
      {
         boolean ambiguous = false;
         
         RtmSmartFilterToken filterToken = filterTokens.get( i );
         
         // next token
         int startI = i++;
         
         // Look ahead if we have a row of equal operators
         for ( ; i < cnt
            && filterTokens.get( i ).operatorType == filterToken.operatorType; ++i )
         {
            ambiguous = true;
            
            // remove equal token in row
            filterTokens.set( i, null );
         }
         
         if ( ambiguous )
            // Remove first token of the row
            filterTokens.set( startI, null );
      }
      
      for ( Iterator< RtmSmartFilterToken > i = filterTokens.iterator(); i.hasNext(); )
      {
         if ( i.next() == null )
            i.remove();
      }
      
      return filterTokens;
   }
}

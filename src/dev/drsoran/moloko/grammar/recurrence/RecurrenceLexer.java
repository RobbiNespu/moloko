// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g 2011-10-13 06:01:13

package dev.drsoran.moloko.grammar.recurrence;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

import dev.drsoran.moloko.grammar.LexerException;


public class RecurrenceLexer extends Lexer
{
   public static final int THIRD = 26;
   
   public static final int NUM_TWO = 30;
   
   public static final int NUM_NINE = 37;
   
   public static final int WEDNESDAY = 41;
   
   public static final int THE = 10;
   
   public static final int FOR = 16;
   
   public static final int NUM_SIX = 34;
   
   public static final int AND = 18;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 48;
   
   public static final int FRIDAY = 43;
   
   public static final int IN = 13;
   
   public static final int NUM_THREE = 31;
   
   public static final int COMMA = 19;
   
   public static final int NUM_ONE = 29;
   
   public static final int LAST = 20;
   
   public static final int DOT = 21;
   
   public static final int ST_S = 22;
   
   public static final int NUM_EIGHT = 36;
   
   public static final int FOURTH = 27;
   
   public static final int BIWEEKLY = 8;
   
   public static final int SECOND = 24;
   
   public static final int OTHER = 25;
   
   public static final int NUM_FOUR = 32;
   
   public static final int SATURDAY = 44;
   
   public static final int NUMBER = 52;
   
   public static final int NUM_SEVEN = 35;
   
   public static final int EVERY = 4;
   
   public static final int WEEKEND = 46;
   
   public static final int ON = 9;
   
   public static final int MONDAY = 39;
   
   public static final int SUNDAY = 45;
   
   public static final int INT = 17;
   
   public static final int MINUS = 51;
   
   public static final int AFTER = 5;
   
   public static final int OF = 14;
   
   public static final int YEARS = 12;
   
   public static final int NUM_FIVE = 33;
   
   public static final int FIFTH = 28;
   
   public static final int DAYS = 6;
   
   public static final int NUM_TEN = 38;
   
   public static final int WEEKS = 7;
   
   public static final int WS = 53;
   
   public static final int THURSDAY = 42;
   
   public static final int UNTIL = 15;
   
   public static final int MONTHS = 11;
   
   public static final int WEEKDAY_LIT = 47;
   
   public static final int TIMES = 50;
   
   public static final int TUESDAY = 40;
   
   public static final int FIRST = 23;
   
   public static final int STRING = 49;
   
   

   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
   }
   


   // delegates
   // delegators
   
   public RecurrenceLexer()
   {
      ;
   }
   


   public RecurrenceLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RecurrenceLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g";
   }
   


   // $ANTLR start "EVERY"
   public final void mEVERY() throws RecognitionException
   {
      try
      {
         int _type = EVERY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:291:15:
         // ( 'every' | 'each' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == 'e' ) )
         {
            int LA1_1 = input.LA( 2 );
            
            if ( ( LA1_1 == 'v' ) )
            {
               alt1 = 1;
            }
            else if ( ( LA1_1 == 'a' ) )
            {
               alt1 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     1,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  1,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt1 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:291:17:
               // 'every'
            {
               match( "every" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:291:27:
               // 'each'
            {
               match( "each" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "EVERY"
   
   // $ANTLR start "AFTER"
   public final void mAFTER() throws RecognitionException
   {
      try
      {
         int _type = AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:293:15:
         // ( 'after' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:293:17:
         // 'after'
         {
            match( "after" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AFTER"
   
   // $ANTLR start "BIWEEKLY"
   public final void mBIWEEKLY() throws RecognitionException
   {
      try
      {
         int _type = BIWEEKLY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:295:15:
         // ( 'fortnight' | 'biweekly' )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 'f' ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == 'b' ) )
         {
            alt2 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  2,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt2 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:295:17:
               // 'fortnight'
            {
               match( "fortnight" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:295:31:
               // 'biweekly'
            {
               match( "biweekly" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "BIWEEKLY"
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:297:15:
         // ( 'years' | 'year' | 'yrs' | 'yr' )
         int alt3 = 4;
         alt3 = dfa3.predict( input );
         switch ( alt3 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:297:17:
               // 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:297:27:
               // 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:297:36:
               // 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:297:44:
               // 'yr'
            {
               match( "yr" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "YEARS"
   
   // $ANTLR start "MONTHS"
   public final void mMONTHS() throws RecognitionException
   {
      try
      {
         int _type = MONTHS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:299:15:
         // ( 'months' | 'month' | 'mons' | 'mon' )
         int alt4 = 4;
         alt4 = dfa4.predict( input );
         switch ( alt4 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:299:17:
               // 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:299:28:
               // 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:299:38:
               // 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:299:47:
               // 'mon'
            {
               match( "mon" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MONTHS"
   
   // $ANTLR start "WEEKS"
   public final void mWEEKS() throws RecognitionException
   {
      try
      {
         int _type = WEEKS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:301:15:
         // ( 'weeks' | 'week' | 'wks' | 'wk' )
         int alt5 = 4;
         alt5 = dfa5.predict( input );
         switch ( alt5 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:301:17:
               // 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:301:27:
               // 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:301:36:
               // 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:301:44:
               // 'wk'
            {
               match( "wk" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WEEKS"
   
   // $ANTLR start "DAYS"
   public final void mDAYS() throws RecognitionException
   {
      try
      {
         int _type = DAYS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:303:15:
         // ( 'days' | 'day' )
         int alt6 = 2;
         int LA6_0 = input.LA( 1 );
         
         if ( ( LA6_0 == 'd' ) )
         {
            int LA6_1 = input.LA( 2 );
            
            if ( ( LA6_1 == 'a' ) )
            {
               int LA6_2 = input.LA( 3 );
               
               if ( ( LA6_2 == 'y' ) )
               {
                  int LA6_3 = input.LA( 4 );
                  
                  if ( ( LA6_3 == 's' ) )
                  {
                     alt6 = 1;
                  }
                  else
                  {
                     alt6 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        6,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     6,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  6,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt6 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:303:17:
               // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:303:26:
               // 'day'
            {
               match( "day" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "DAYS"
   
   // $ANTLR start "MONTH"
   public final void mMONTH() throws RecognitionException
   {
      try
      {
         int _type = MONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:15:
         // ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' |
         // 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' |
         // 'december' | 'dec' )
         int alt7 = 24;
         alt7 = dfa7.predict( input );
         switch ( alt7 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:17:
               // 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:31:
               // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:40:
               // 'february'
            {
               match( "february" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:53:
               // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:65:
               // 'march'
            {
               match( "march" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:75:
               // 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:88:
               // 'april'
            {
               match( "april" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:305:98:
               // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:17:
               // 'may'
            {
               match( "may" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:31:
               // 'june'
            {
               match( "june" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:40:
               // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:53:
               // 'july'
            {
               match( "july" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:65:
               // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:75:
               // 'august'
            {
               match( "august" );
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:88:
               // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:17:
               // 'september'
            {
               match( "september" );
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:31:
               // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:40:
               // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:53:
               // 'october'
            {
               match( "october" );
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:65:
               // 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:75:
               // 'november'
            {
               match( "november" );
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:307:88:
               // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 23:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:17:
               // 'december'
            {
               match( "december" );
               
            }
               break;
            case 24:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:31:
               // 'dec'
            {
               match( "dec" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MONTH"
   
   // $ANTLR start "WEEKDAY_LIT"
   public final void mWEEKDAY_LIT() throws RecognitionException
   {
      try
      {
         int _type = WEEKDAY_LIT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:15:
         // ( 'weekday' ( 's' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:17:
         // 'weekday' ( 's' )?
         {
            match( "weekday" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:26:
            // ( 's' )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == 's' ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:26:
                  // 's'
               {
                  match( 's' );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WEEKDAY_LIT"
   
   // $ANTLR start "WEEKEND"
   public final void mWEEKEND() throws RecognitionException
   {
      try
      {
         int _type = WEEKEND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:15:
         // ( 'weekend' ( 's' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:17:
         // 'weekend' ( 's' )?
         {
            match( "weekend" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:26:
            // ( 's' )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == 's' ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:26:
                  // 's'
               {
                  match( 's' );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WEEKEND"
   
   // $ANTLR start "MONDAY"
   public final void mMONDAY() throws RecognitionException
   {
      try
      {
         int _type = MONDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:15:
         // ( 'monday' | 'mon' )
         int alt10 = 2;
         int LA10_0 = input.LA( 1 );
         
         if ( ( LA10_0 == 'm' ) )
         {
            int LA10_1 = input.LA( 2 );
            
            if ( ( LA10_1 == 'o' ) )
            {
               int LA10_2 = input.LA( 3 );
               
               if ( ( LA10_2 == 'n' ) )
               {
                  int LA10_3 = input.LA( 4 );
                  
                  if ( ( LA10_3 == 'd' ) )
                  {
                     alt10 = 1;
                  }
                  else
                  {
                     alt10 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        10,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  10,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt10 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:17:
               // 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:31:
               // 'mon'
            {
               match( "mon" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MONDAY"
   
   // $ANTLR start "TUESDAY"
   public final void mTUESDAY() throws RecognitionException
   {
      try
      {
         int _type = TUESDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:15:
         // ( 'tuesday' | 'tue' )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == 't' ) )
         {
            int LA11_1 = input.LA( 2 );
            
            if ( ( LA11_1 == 'u' ) )
            {
               int LA11_2 = input.LA( 3 );
               
               if ( ( LA11_2 == 'e' ) )
               {
                  int LA11_3 = input.LA( 4 );
                  
                  if ( ( LA11_3 == 's' ) )
                  {
                     alt11 = 1;
                  }
                  else
                  {
                     alt11 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        11,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     11,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  11,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt11 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:17:
               // 'tuesday'
            {
               match( "tuesday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:31:
               // 'tue'
            {
               match( "tue" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TUESDAY"
   
   // $ANTLR start "WEDNESDAY"
   public final void mWEDNESDAY() throws RecognitionException
   {
      try
      {
         int _type = WEDNESDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:318:15:
         // ( 'wednesday' | 'wed' )
         int alt12 = 2;
         int LA12_0 = input.LA( 1 );
         
         if ( ( LA12_0 == 'w' ) )
         {
            int LA12_1 = input.LA( 2 );
            
            if ( ( LA12_1 == 'e' ) )
            {
               int LA12_2 = input.LA( 3 );
               
               if ( ( LA12_2 == 'd' ) )
               {
                  int LA12_3 = input.LA( 4 );
                  
                  if ( ( LA12_3 == 'n' ) )
                  {
                     alt12 = 1;
                  }
                  else
                  {
                     alt12 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        12,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     12,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  12,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt12 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:318:17:
               // 'wednesday'
            {
               match( "wednesday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:318:31:
               // 'wed'
            {
               match( "wed" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WEDNESDAY"
   
   // $ANTLR start "THURSDAY"
   public final void mTHURSDAY() throws RecognitionException
   {
      try
      {
         int _type = THURSDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:320:15:
         // ( 'thursday' | 'thu' )
         int alt13 = 2;
         int LA13_0 = input.LA( 1 );
         
         if ( ( LA13_0 == 't' ) )
         {
            int LA13_1 = input.LA( 2 );
            
            if ( ( LA13_1 == 'h' ) )
            {
               int LA13_2 = input.LA( 3 );
               
               if ( ( LA13_2 == 'u' ) )
               {
                  int LA13_3 = input.LA( 4 );
                  
                  if ( ( LA13_3 == 'r' ) )
                  {
                     alt13 = 1;
                  }
                  else
                  {
                     alt13 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        13,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     13,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  13,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt13 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:320:17:
               // 'thursday'
            {
               match( "thursday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:320:31:
               // 'thu'
            {
               match( "thu" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "THURSDAY"
   
   // $ANTLR start "FRIDAY"
   public final void mFRIDAY() throws RecognitionException
   {
      try
      {
         int _type = FRIDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:322:15:
         // ( 'friday' | 'fri' )
         int alt14 = 2;
         int LA14_0 = input.LA( 1 );
         
         if ( ( LA14_0 == 'f' ) )
         {
            int LA14_1 = input.LA( 2 );
            
            if ( ( LA14_1 == 'r' ) )
            {
               int LA14_2 = input.LA( 3 );
               
               if ( ( LA14_2 == 'i' ) )
               {
                  int LA14_3 = input.LA( 4 );
                  
                  if ( ( LA14_3 == 'd' ) )
                  {
                     alt14 = 1;
                  }
                  else
                  {
                     alt14 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  14,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt14 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:322:17:
               // 'friday'
            {
               match( "friday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:322:31:
               // 'fri'
            {
               match( "fri" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FRIDAY"
   
   // $ANTLR start "SATURDAY"
   public final void mSATURDAY() throws RecognitionException
   {
      try
      {
         int _type = SATURDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:324:15:
         // ( 'saturday' | 'sat' )
         int alt15 = 2;
         int LA15_0 = input.LA( 1 );
         
         if ( ( LA15_0 == 's' ) )
         {
            int LA15_1 = input.LA( 2 );
            
            if ( ( LA15_1 == 'a' ) )
            {
               int LA15_2 = input.LA( 3 );
               
               if ( ( LA15_2 == 't' ) )
               {
                  int LA15_3 = input.LA( 4 );
                  
                  if ( ( LA15_3 == 'u' ) )
                  {
                     alt15 = 1;
                  }
                  else
                  {
                     alt15 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        15,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     15,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  15,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt15 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:324:17:
               // 'saturday'
            {
               match( "saturday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:324:31:
               // 'sat'
            {
               match( "sat" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "SATURDAY"
   
   // $ANTLR start "SUNDAY"
   public final void mSUNDAY() throws RecognitionException
   {
      try
      {
         int _type = SUNDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:326:15:
         // ( 'sunday' | 'sun' )
         int alt16 = 2;
         int LA16_0 = input.LA( 1 );
         
         if ( ( LA16_0 == 's' ) )
         {
            int LA16_1 = input.LA( 2 );
            
            if ( ( LA16_1 == 'u' ) )
            {
               int LA16_2 = input.LA( 3 );
               
               if ( ( LA16_2 == 'n' ) )
               {
                  int LA16_3 = input.LA( 4 );
                  
                  if ( ( LA16_3 == 'd' ) )
                  {
                     alt16 = 1;
                  }
                  else
                  {
                     alt16 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        16,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     16,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  16,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt16 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:326:17:
               // 'sunday'
            {
               match( "sunday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:326:31:
               // 'sun'
            {
               match( "sun" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "SUNDAY"
   
   // $ANTLR start "FIRST"
   public final void mFIRST() throws RecognitionException
   {
      try
      {
         int _type = FIRST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:328:15:
         // ( 'first' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:328:17:
         // 'first'
         {
            match( "first" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FIRST"
   
   // $ANTLR start "SECOND"
   public final void mSECOND() throws RecognitionException
   {
      try
      {
         int _type = SECOND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:330:15:
         // ( 'second' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:330:17:
         // 'second'
         {
            match( "second" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "SECOND"
   
   // $ANTLR start "THIRD"
   public final void mTHIRD() throws RecognitionException
   {
      try
      {
         int _type = THIRD;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:332:15:
         // ( 'third' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:332:17:
         // 'third'
         {
            match( "third" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "THIRD"
   
   // $ANTLR start "FOURTH"
   public final void mFOURTH() throws RecognitionException
   {
      try
      {
         int _type = FOURTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:334:15:
         // ( 'fourth' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:334:17:
         // 'fourth'
         {
            match( "fourth" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FOURTH"
   
   // $ANTLR start "FIFTH"
   public final void mFIFTH() throws RecognitionException
   {
      try
      {
         int _type = FIFTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:336:15:
         // ( 'fifth' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:336:17:
         // 'fifth'
         {
            match( "fifth" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FIFTH"
   
   // $ANTLR start "LAST"
   public final void mLAST() throws RecognitionException
   {
      try
      {
         int _type = LAST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:338:15:
         // ( 'last' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:338:17:
         // 'last'
         {
            match( "last" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "LAST"
   
   // $ANTLR start "OTHER"
   public final void mOTHER() throws RecognitionException
   {
      try
      {
         int _type = OTHER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:340:15:
         // ( 'other' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:340:17:
         // 'other'
         {
            match( "other" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OTHER"
   
   // $ANTLR start "ST_S"
   public final void mST_S() throws RecognitionException
   {
      try
      {
         int _type = ST_S;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:342:15:
         // ( 'st' | 'nd' | 'rd' | 'th' )
         int alt17 = 4;
         switch ( input.LA( 1 ) )
         {
            case 's':
            {
               alt17 = 1;
            }
               break;
            case 'n':
            {
               alt17 = 2;
            }
               break;
            case 'r':
            {
               alt17 = 3;
            }
               break;
            case 't':
            {
               alt17 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     17,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt17 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:342:17:
               // 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:342:24:
               // 'nd'
            {
               match( "nd" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:342:31:
               // 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:342:38:
               // 'th'
            {
               match( "th" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "ST_S"
   
   // $ANTLR start "NUM_ONE"
   public final void mNUM_ONE() throws RecognitionException
   {
      try
      {
         int _type = NUM_ONE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:344:15:
         // ( 'one' | 'a' )
         int alt18 = 2;
         int LA18_0 = input.LA( 1 );
         
         if ( ( LA18_0 == 'o' ) )
         {
            alt18 = 1;
         }
         else if ( ( LA18_0 == 'a' ) )
         {
            alt18 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  18,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt18 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:344:17:
               // 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:344:25:
               // 'a'
            {
               match( 'a' );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_ONE"
   
   // $ANTLR start "NUM_TWO"
   public final void mNUM_TWO() throws RecognitionException
   {
      try
      {
         int _type = NUM_TWO;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:346:15:
         // ( 'two' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:346:17:
         // 'two'
         {
            match( "two" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_TWO"
   
   // $ANTLR start "NUM_THREE"
   public final void mNUM_THREE() throws RecognitionException
   {
      try
      {
         int _type = NUM_THREE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:348:15:
         // ( 'three' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:348:17:
         // 'three'
         {
            match( "three" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_THREE"
   
   // $ANTLR start "NUM_FOUR"
   public final void mNUM_FOUR() throws RecognitionException
   {
      try
      {
         int _type = NUM_FOUR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:350:15:
         // ( 'four' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:350:17:
         // 'four'
         {
            match( "four" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_FOUR"
   
   // $ANTLR start "NUM_FIVE"
   public final void mNUM_FIVE() throws RecognitionException
   {
      try
      {
         int _type = NUM_FIVE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:352:15:
         // ( 'five' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:352:17:
         // 'five'
         {
            match( "five" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_FIVE"
   
   // $ANTLR start "NUM_SIX"
   public final void mNUM_SIX() throws RecognitionException
   {
      try
      {
         int _type = NUM_SIX;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:354:15:
         // ( 'six' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:354:17:
         // 'six'
         {
            match( "six" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_SIX"
   
   // $ANTLR start "NUM_SEVEN"
   public final void mNUM_SEVEN() throws RecognitionException
   {
      try
      {
         int _type = NUM_SEVEN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:356:15:
         // ( 'seven' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:356:17:
         // 'seven'
         {
            match( "seven" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_SEVEN"
   
   // $ANTLR start "NUM_EIGHT"
   public final void mNUM_EIGHT() throws RecognitionException
   {
      try
      {
         int _type = NUM_EIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:358:15:
         // ( 'eight' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:358:17:
         // 'eight'
         {
            match( "eight" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_EIGHT"
   
   // $ANTLR start "NUM_NINE"
   public final void mNUM_NINE() throws RecognitionException
   {
      try
      {
         int _type = NUM_NINE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:360:15:
         // ( 'nine' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:360:17:
         // 'nine'
         {
            match( "nine" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_NINE"
   
   // $ANTLR start "NUM_TEN"
   public final void mNUM_TEN() throws RecognitionException
   {
      try
      {
         int _type = NUM_TEN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:362:15:
         // ( 'ten' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:362:17:
         // 'ten'
         {
            match( "ten" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_TEN"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:364:15:
         // ( 'and' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:364:17:
         // 'and'
         {
            match( "and" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AND"
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:366:15:
         // ( 'in' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:366:17:
         // 'in'
         {
            match( "in" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "IN"
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:368:15:
         // ( 'on' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:368:17:
         // 'on'
         {
            match( "on" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "ON"
   
   // $ANTLR start "OF"
   public final void mOF() throws RecognitionException
   {
      try
      {
         int _type = OF;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:370:15:
         // ( 'of' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:370:17:
         // 'of'
         {
            match( "of" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OF"
   
   // $ANTLR start "THE"
   public final void mTHE() throws RecognitionException
   {
      try
      {
         int _type = THE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:372:15:
         // ( 'the' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:372:17:
         // 'the'
         {
            match( "the" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "THE"
   
   // $ANTLR start "UNTIL"
   public final void mUNTIL() throws RecognitionException
   {
      try
      {
         int _type = UNTIL;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:374:15:
         // ( 'until' STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:374:17:
         // 'until' STRING
         {
            match( "until" );
            
            mSTRING();
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "UNTIL"
   
   // $ANTLR start "FOR"
   public final void mFOR() throws RecognitionException
   {
      try
      {
         int _type = FOR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:376:15:
         // ( 'for' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:376:17:
         // 'for'
         {
            match( "for" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FOR"
   
   // $ANTLR start "TIMES"
   public final void mTIMES() throws RecognitionException
   {
      try
      {
         int _type = TIMES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:378:15:
         // ( 'times' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:378:17:
         // 'times'
         {
            match( "times" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TIMES"
   
   // $ANTLR start "DOT"
   public final void mDOT() throws RecognitionException
   {
      try
      {
         int _type = DOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:380:15:
         // ( '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:380:17:
         // '.'
         {
            match( '.' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "DOT"
   
   // $ANTLR start "MINUS"
   public final void mMINUS() throws RecognitionException
   {
      try
      {
         int _type = MINUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:382:15:
         // ( '-' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:382:17:
         // '-'
         {
            match( '-' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MINUS"
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:384:15:
         // ( ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:384:17:
         // ','
         {
            match( ',' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "COMMA"
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:387:15:
         // ( '0' .. '9' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:387:17:
         // '0' .. '9'
         {
            matchRange( '0', '9' );
            
         }
         
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUMBER"
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:15:
         // ( ( MINUS )? ( NUMBER )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:17:
         // ( MINUS )? ( NUMBER )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:17:
            // ( MINUS )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == '-' ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:17:
                  // MINUS
               {
                  mMINUS();
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:24:
            // ( NUMBER )+
            int cnt20 = 0;
            loop20: do
            {
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( ( LA20_0 >= '0' && LA20_0 <= '9' ) ) )
               {
                  alt20 = 1;
               }
               
               switch ( alt20 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:24:
                     // NUMBER
                  {
                     mNUMBER();
                     
                  }
                     break;
                  
                  default :
                     if ( cnt20 >= 1 )
                        break loop20;
                     EarlyExitException eee = new EarlyExitException( 20, input );
                     throw eee;
               }
               cnt20++;
            }
            while ( true );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "INT"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:392:15:
         // ( ( ' ' | . )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:392:17:
         // ( ' ' | . )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:392:17:
            // ( ' ' | . )+
            int cnt21 = 0;
            loop21: do
            {
               int alt21 = 3;
               int LA21_0 = input.LA( 1 );
               
               if ( ( LA21_0 == ' ' ) )
               {
                  alt21 = 1;
               }
               else if ( ( ( LA21_0 >= '\u0000' && LA21_0 <= '\u001F' ) || ( LA21_0 >= '!' && LA21_0 <= '\uFFFF' ) ) )
               {
                  alt21 = 2;
               }
               
               switch ( alt21 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:392:18:
                     // ' '
                  {
                     match( ' ' );
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:392:22:
                     // .
                  {
                     matchAny();
                     
                  }
                     break;
                  
                  default :
                     if ( cnt21 >= 1 )
                        break loop21;
                     EarlyExitException eee = new EarlyExitException( 21, input );
                     throw eee;
               }
               cnt21++;
            }
            while ( true );
            
         }
         
      }
      finally
      {
      }
   }
   


   // $ANTLR end "STRING"
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:394:15:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:394:17:
         // ( ' ' | '\\t' | '\\r' | '\\n' )
         {
            if ( ( input.LA( 1 ) >= '\t' && input.LA( 1 ) <= '\n' )
               || input.LA( 1 ) == '\r' || input.LA( 1 ) == ' ' )
            {
               input.consume();
               
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               recover( mse );
               throw mse;
            }
            
            _channel = HIDDEN;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WS"
   
   public void mTokens() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:8: (
      // EVERY | AFTER | BIWEEKLY | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY |
      // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER |
      // ST_S | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE |
      // NUM_TEN | AND | IN | ON | OF | THE | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS )
      int alt22 = 48;
      alt22 = dfa22.predict( input );
      switch ( alt22 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:10:
            // EVERY
         {
            mEVERY();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:16:
            // AFTER
         {
            mAFTER();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:22:
            // BIWEEKLY
         {
            mBIWEEKLY();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:31:
            // YEARS
         {
            mYEARS();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:37:
            // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:44:
            // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:50:
            // DAYS
         {
            mDAYS();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:55:
            // MONTH
         {
            mMONTH();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:61:
            // WEEKDAY_LIT
         {
            mWEEKDAY_LIT();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:73:
            // WEEKEND
         {
            mWEEKEND();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:81:
            // MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:88:
            // TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:96:
            // WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:106:
            // THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:115:
            // FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:122:
            // SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:131:
            // SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:138:
            // FIRST
         {
            mFIRST();
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:144:
            // SECOND
         {
            mSECOND();
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:151:
            // THIRD
         {
            mTHIRD();
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:157:
            // FOURTH
         {
            mFOURTH();
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:164:
            // FIFTH
         {
            mFIFTH();
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:170:
            // LAST
         {
            mLAST();
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:175:
            // OTHER
         {
            mOTHER();
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:181:
            // ST_S
         {
            mST_S();
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:186:
            // NUM_ONE
         {
            mNUM_ONE();
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:194:
            // NUM_TWO
         {
            mNUM_TWO();
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:202:
            // NUM_THREE
         {
            mNUM_THREE();
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:212:
            // NUM_FOUR
         {
            mNUM_FOUR();
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:221:
            // NUM_FIVE
         {
            mNUM_FIVE();
            
         }
            break;
         case 31:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:230:
            // NUM_SIX
         {
            mNUM_SIX();
            
         }
            break;
         case 32:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:238:
            // NUM_SEVEN
         {
            mNUM_SEVEN();
            
         }
            break;
         case 33:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:248:
            // NUM_EIGHT
         {
            mNUM_EIGHT();
            
         }
            break;
         case 34:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:258:
            // NUM_NINE
         {
            mNUM_NINE();
            
         }
            break;
         case 35:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:267:
            // NUM_TEN
         {
            mNUM_TEN();
            
         }
            break;
         case 36:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:275:
            // AND
         {
            mAND();
            
         }
            break;
         case 37:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:279:
            // IN
         {
            mIN();
            
         }
            break;
         case 38:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:282:
            // ON
         {
            mON();
            
         }
            break;
         case 39:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:285:
            // OF
         {
            mOF();
            
         }
            break;
         case 40:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:288:
            // THE
         {
            mTHE();
            
         }
            break;
         case 41:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:292:
            // UNTIL
         {
            mUNTIL();
            
         }
            break;
         case 42:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:298:
            // FOR
         {
            mFOR();
            
         }
            break;
         case 43:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:302:
            // TIMES
         {
            mTIMES();
            
         }
            break;
         case 44:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:308:
            // DOT
         {
            mDOT();
            
         }
            break;
         case 45:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:312:
            // MINUS
         {
            mMINUS();
            
         }
            break;
         case 46:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:318:
            // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 47:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:324:
            // INT
         {
            mINT();
            
         }
            break;
         case 48:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:328:
            // WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA3 dfa3 = new DFA3( this );
   
   protected DFA4 dfa4 = new DFA4( this );
   
   protected DFA5 dfa5 = new DFA5( this );
   
   protected DFA7 dfa7 = new DFA7( this );
   
   protected DFA22 dfa22 = new DFA22( this );
   
   static final String DFA3_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA3_eofS = "\12\uffff";
   
   static final String DFA3_minS = "\1\171\1\145\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA3_maxS = "\1\171\1\162\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA3_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA3_specialS = "\12\uffff}>";
   
   static final String[] DFA3_transitionS =
   { "\1\1", "\1\2\14\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
   static final short[] DFA3_eot = DFA.unpackEncodedString( DFA3_eotS );
   
   static final short[] DFA3_eof = DFA.unpackEncodedString( DFA3_eofS );
   
   static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars( DFA3_minS );
   
   static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars( DFA3_maxS );
   
   static final short[] DFA3_accept = DFA.unpackEncodedString( DFA3_acceptS );
   
   static final short[] DFA3_special = DFA.unpackEncodedString( DFA3_specialS );
   
   static final short[][] DFA3_transition;
   
   static
   {
      int numStates = DFA3_transitionS.length;
      DFA3_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA3_transition[ i ] = DFA.unpackEncodedString( DFA3_transitionS[ i ] );
      }
   }
   
   
   class DFA3 extends DFA
   {
      
      public DFA3( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 3;
         this.eot = DFA3_eot;
         this.eof = DFA3_eof;
         this.min = DFA3_min;
         this.max = DFA3_max;
         this.accept = DFA3_accept;
         this.special = DFA3_special;
         this.transition = DFA3_transition;
      }
      


      public String getDescription()
      {
         return "297:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' );";
      }
   }
   
   static final String DFA4_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA4_eofS = "\12\uffff";
   
   static final String DFA4_minS = "\1\155\1\157\1\156\1\163\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA4_maxS = "\1\155\1\157\1\156\1\164\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA4_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA4_specialS = "\12\uffff}>";
   
   static final String[] DFA4_transitionS =
   { "\1\1", "\1\2", "\1\3", "\1\5\1\4", "\1\7", "", "", "\1\10", "", "" };
   
   static final short[] DFA4_eot = DFA.unpackEncodedString( DFA4_eotS );
   
   static final short[] DFA4_eof = DFA.unpackEncodedString( DFA4_eofS );
   
   static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars( DFA4_minS );
   
   static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars( DFA4_maxS );
   
   static final short[] DFA4_accept = DFA.unpackEncodedString( DFA4_acceptS );
   
   static final short[] DFA4_special = DFA.unpackEncodedString( DFA4_specialS );
   
   static final short[][] DFA4_transition;
   
   static
   {
      int numStates = DFA4_transitionS.length;
      DFA4_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA4_transition[ i ] = DFA.unpackEncodedString( DFA4_transitionS[ i ] );
      }
   }
   
   
   class DFA4 extends DFA
   {
      
      public DFA4( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 4;
         this.eot = DFA4_eot;
         this.eof = DFA4_eof;
         this.min = DFA4_min;
         this.max = DFA4_max;
         this.accept = DFA4_accept;
         this.special = DFA4_special;
         this.transition = DFA4_transition;
      }
      


      public String getDescription()
      {
         return "299:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' );";
      }
   }
   
   static final String DFA5_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA5_eofS = "\12\uffff";
   
   static final String DFA5_minS = "\1\167\2\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA5_maxS = "\1\167\1\153\1\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA5_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA5_specialS = "\12\uffff}>";
   
   static final String[] DFA5_transitionS =
   { "\1\1", "\1\2\5\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
   static final short[] DFA5_eot = DFA.unpackEncodedString( DFA5_eotS );
   
   static final short[] DFA5_eof = DFA.unpackEncodedString( DFA5_eofS );
   
   static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars( DFA5_minS );
   
   static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars( DFA5_maxS );
   
   static final short[] DFA5_accept = DFA.unpackEncodedString( DFA5_acceptS );
   
   static final short[] DFA5_special = DFA.unpackEncodedString( DFA5_specialS );
   
   static final short[][] DFA5_transition;
   
   static
   {
      int numStates = DFA5_transitionS.length;
      DFA5_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA5_transition[ i ] = DFA.unpackEncodedString( DFA5_transitionS[ i ] );
      }
   }
   
   
   class DFA5 extends DFA
   {
      
      public DFA5( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 5;
         this.eot = DFA5_eot;
         this.eof = DFA5_eof;
         this.min = DFA5_min;
         this.max = DFA5_max;
         this.accept = DFA5_accept;
         this.special = DFA5_special;
         this.transition = DFA5_transition;
      }
      


      public String getDescription()
      {
         return "301:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' );";
      }
   }
   
   static final String DFA7_eotS = "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"
      + "\1\62\1\64\16\uffff\1\66\11\uffff";
   
   static final String DFA7_eofS = "\67\uffff";
   
   static final String DFA7_minS = "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"
      + "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"
      + "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
   
   static final String DFA7_maxS = "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"
      + "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"
      + "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"
      + "\11\uffff";
   
   static final String DFA7_acceptS = "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"
      + "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"
      + "\30\1\20\1\21";
   
   static final String DFA7_specialS = "\67\uffff}>";
   
   static final String[] DFA7_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13", "\1\14",
    "\1\15\4\uffff\1\16", "\1\17", "\1\20", "\1\21", "\1\22", "\1\23",
    "\1\25\1\uffff\1\24", "\1\26", "\1\27\6\uffff\1\30", "\1\31", "\1\32",
    "\1\33", "\1\34", "\1\35", "\1\36", "\1\37", "\1\41", "\1\43", "\1\45",
    "\1\47", "", "\1\51", "\1\53", "\1\55", "\1\57", "\1\61", "\1\63", "", "",
    "", "", "", "", "", "", "", "", "", "", "", "", "\1\65", "", "", "", "",
    "", "", "", "", "" };
   
   static final short[] DFA7_eot = DFA.unpackEncodedString( DFA7_eotS );
   
   static final short[] DFA7_eof = DFA.unpackEncodedString( DFA7_eofS );
   
   static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars( DFA7_minS );
   
   static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars( DFA7_maxS );
   
   static final short[] DFA7_accept = DFA.unpackEncodedString( DFA7_acceptS );
   
   static final short[] DFA7_special = DFA.unpackEncodedString( DFA7_specialS );
   
   static final short[][] DFA7_transition;
   
   static
   {
      int numStates = DFA7_transitionS.length;
      DFA7_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA7_transition[ i ] = DFA.unpackEncodedString( DFA7_transitionS[ i ] );
      }
   }
   
   
   class DFA7 extends DFA
   {
      
      public DFA7( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 7;
         this.eot = DFA7_eot;
         this.eof = DFA7_eof;
         this.min = DFA7_min;
         this.max = DFA7_max;
         this.accept = DFA7_accept;
         this.special = DFA7_special;
         this.transition = DFA7_transition;
      }
      


      public String getDescription()
      {
         return "305:1: MONTH : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
      }
   }
   
   static final String DFA22_eotS = "\2\uffff\1\33\20\uffff\1\60\24\uffff\1\73\3\uffff\1\17\4\uffff"
      + "\1\100\4\uffff\1\102\12\uffff\1\106\2\uffff\1\41\4\uffff";
   
   static final String DFA22_eofS = "\111\uffff";
   
   static final String DFA22_minS = "\1\11\1\141\1\146\1\145\2\uffff\1\141\1\145\1\141\1\uffff\1\141"
      + "\1\143\1\144\1\145\5\uffff\1\60\10\uffff\1\162\1\uffff\1\146\1\156"
      + "\1\144\2\uffff\1\143\4\uffff\1\145\3\uffff\1\145\4\uffff\1\164\1"
      + "\162\3\uffff\1\144\1\153\11\uffff\1\164\2\uffff\1\144\4\uffff";
   
   static final String DFA22_maxS = "\1\171\1\166\1\165\1\162\2\uffff\1\157\1\153\1\145\1\uffff\1\165"
      + "\1\164\1\157\1\167\5\uffff\1\71\10\uffff\1\165\1\uffff\1\166\1\156"
      + "\1\145\2\uffff\1\166\4\uffff\1\145\3\uffff\1\165\4\uffff\1\164\1"
      + "\162\3\uffff\1\144\1\153\11\uffff\1\164\2\uffff\1\145\4\uffff";
   
   static final String DFA22_acceptS = "\4\uffff\1\3\1\4\3\uffff\1\10\4\uffff\1\27\1\31\1\45\1\51\1\54"
      + "\1\uffff\1\56\1\57\1\60\1\1\1\41\1\2\1\44\1\32\1\uffff\1\17\3\uffff"
      + "\1\6\1\7\1\uffff\1\20\1\21\1\37\1\30\1\uffff\1\47\1\42\1\14\1\uffff"
      + "\1\33\1\43\1\53\1\55\2\uffff\1\22\1\26\1\36\2\uffff\1\15\1\23\1"
      + "\40\1\46\1\16\1\24\1\34\1\50\1\52\1\uffff\1\5\1\13\1\uffff\1\25"
      + "\1\35\1\11\1\12";
   
   static final String DFA22_specialS = "\111\uffff}>";
   
   static final String[] DFA22_transitionS =
   {
    "\2\26\2\uffff\1\26\22\uffff\1\26\13\uffff\1\24\1\23\1\22\1"
       + "\uffff\12\25\47\uffff\1\2\1\4\1\uffff\1\10\1\1\1\3\2\uffff\1"
       + "\20\1\11\1\uffff\1\16\1\6\1\14\1\13\2\uffff\1\17\1\12\1\15\1"
       + "\21\1\uffff\1\7\1\uffff\1\5", "\1\27\7\uffff\1\30\14\uffff\1\27",
    "\1\31\7\uffff\1\32\1\uffff\1\11\4\uffff\1\11",
    "\1\11\3\uffff\1\36\5\uffff\1\34\2\uffff\1\35", "", "",
    "\1\11\15\uffff\1\37", "\1\40\5\uffff\1\41", "\1\42\3\uffff\1\11", "",
    "\1\44\3\uffff\1\43\3\uffff\1\46\12\uffff\1\17\1\45",
    "\1\11\2\uffff\1\51\7\uffff\1\50\5\uffff\1\47",
    "\1\17\4\uffff\1\52\5\uffff\1\11",
    "\1\56\2\uffff\1\54\1\57\13\uffff\1\53\1\uffff\1\55", "", "", "", "", "",
    "\12\25", "", "", "", "", "", "", "", "", "\1\61\2\uffff\1\62", "",
    "\1\64\13\uffff\1\63\3\uffff\1\65", "\1\66", "\1\70\1\67", "", "",
    "\1\71\14\uffff\1\11\5\uffff\1\72", "", "", "", "", "\1\33", "", "", "",
    "\1\77\3\uffff\1\75\10\uffff\1\76\2\uffff\1\74", "", "", "", "", "\1\4",
    "\1\101", "", "", "", "\1\103", "\1\104", "", "", "", "", "", "", "", "",
    "", "\1\105", "", "", "\1\107\1\110", "", "", "", "" };
   
   static final short[] DFA22_eot = DFA.unpackEncodedString( DFA22_eotS );
   
   static final short[] DFA22_eof = DFA.unpackEncodedString( DFA22_eofS );
   
   static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars( DFA22_minS );
   
   static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars( DFA22_maxS );
   
   static final short[] DFA22_accept = DFA.unpackEncodedString( DFA22_acceptS );
   
   static final short[] DFA22_special = DFA.unpackEncodedString( DFA22_specialS );
   
   static final short[][] DFA22_transition;
   
   static
   {
      int numStates = DFA22_transitionS.length;
      DFA22_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA22_transition[ i ] = DFA.unpackEncodedString( DFA22_transitionS[ i ] );
      }
   }
   
   
   class DFA22 extends DFA
   {
      
      public DFA22( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 22;
         this.eot = DFA22_eot;
         this.eof = DFA22_eof;
         this.min = DFA22_min;
         this.max = DFA22_max;
         this.accept = DFA22_accept;
         this.special = DFA22_special;
         this.transition = DFA22_transition;
      }
      


      public String getDescription()
      {
         return "1:1: Tokens : ( EVERY | AFTER | BIWEEKLY | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | ST_S | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | IN | ON | OF | THE | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS );";
      }
   }
   
}

/* 
 *	Copyright (c) 2013 Ronny R�hricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.test.unit.domain.parsing.rtmsmart;

import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_LIST;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_POSTPONED;

import org.junit.Test;

import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;


public class RtmSmartFilterTokenFixture extends EqualsHashCodeTestCase
{
   
   @Test
   public void testRtmSmartFilterTokenIntStringBoolean()
   {
      new RtmSmartFilterToken( OP_LIST, "List", false );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmSmartFilterToken( OP_LIST, "List", false ).toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new RtmSmartFilterToken( OP_LIST, "List", false );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new RtmSmartFilterToken( OP_POSTPONED, "0", true );
   }
}
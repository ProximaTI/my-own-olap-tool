/* Generated By:JJTree&JavaCC: Do not edit this line. QueryParserTokenManager.java */
package br.com.bi.olapql.language.query;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/** Token Manager. */
public class QueryParserTokenManager implements QueryParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 9:
         jjmatchedKind = 2;
         return jjMoveNfa_0(0, 0);
      case 10:
         jjmatchedKind = 3;
         return jjMoveNfa_0(0, 0);
      case 12:
         jjmatchedKind = 5;
         return jjMoveNfa_0(0, 0);
      case 13:
         jjmatchedKind = 4;
         return jjMoveNfa_0(0, 0);
      case 32:
         jjmatchedKind = 1;
         return jjMoveNfa_0(0, 0);
      case 40:
         jjmatchedKind = 17;
         return jjMoveNfa_0(0, 0);
      case 41:
         jjmatchedKind = 18;
         return jjMoveNfa_0(0, 0);
      case 42:
         jjmatchedKind = 13;
         return jjMoveNfa_0(0, 0);
      case 43:
         jjmatchedKind = 11;
         return jjMoveNfa_0(0, 0);
      case 44:
         jjmatchedKind = 19;
         return jjMoveNfa_0(0, 0);
      case 45:
         jjmatchedKind = 12;
         return jjMoveNfa_0(0, 0);
      case 46:
         jjmatchedKind = 10;
         return jjMoveNfa_0(0, 0);
      case 47:
         jjmatchedKind = 14;
         return jjMoveNfa_0(0, 0);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x194000000L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 69:
         jjmatchedKind = 8;
         return jjMoveNfa_0(0, 0);
      case 76:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 78:
         return jjMoveStringLiteralDfa1_0(0x1000080L);
      case 79:
         return jjMoveStringLiteralDfa1_0(0x20000200L);
      case 80:
         return jjMoveStringLiteralDfa1_0(0x40000000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 84:
         return jjMoveStringLiteralDfa1_0(0x200000000L);
      case 91:
         jjmatchedKind = 15;
         return jjMoveNfa_0(0, 0);
      case 93:
         jjmatchedKind = 16;
         return jjMoveNfa_0(0, 0);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x194000000L);
      case 100:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 101:
         jjmatchedKind = 8;
         return jjMoveNfa_0(0, 0);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x1000080L);
      case 111:
         return jjMoveStringLiteralDfa1_0(0x20000200L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x40000000L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x200000000L);
      case 123:
         jjmatchedKind = 20;
         return jjMoveNfa_0(0, 0);
      case 125:
         jjmatchedKind = 21;
         return jjMoveNfa_0(0, 0);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 0);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 69:
         return jjMoveStringLiteralDfa2_0(active0, 0x240800000L);
      case 73:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000L);
      case 78:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000L);
      case 79:
         if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x184000000L);
      case 85:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000L);
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x240800000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000000L);
      case 111:
         if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x184000000L);
      case 117:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000L);
      case 195:
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      case 227:
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 1);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 1);
   }
   switch(curChar)
   {
      case 66:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000L);
      case 68:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      case 76:
         return jjMoveStringLiteralDfa3_0(active0, 0x4800000L);
      case 77:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 78:
         return jjMoveStringLiteralDfa3_0(active0, 0x82000000L);
      case 79:
         if ((active0 & 0x80L) != 0L)
         {
            jjmatchedKind = 7;
            jjmatchedPos = 2;
         }
         break;
      case 82:
         return jjMoveStringLiteralDfa3_0(active0, 0x240000000L);
      case 83:
         if ((active0 & 0x1000000L) != 0L)
         {
            jjmatchedKind = 24;
            jjmatchedPos = 2;
         }
         break;
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x4800000L);
      case 109:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x82000000L);
      case 111:
         if ((active0 & 0x80L) != 0L)
         {
            jjmatchedKind = 7;
            jjmatchedPos = 2;
         }
         break;
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x240000000L);
      case 115:
         if ((active0 & 0x1000000L) != 0L)
         {
            jjmatchedKind = 24;
            jjmatchedPos = 2;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(0, 2);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 2);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 2);
   }
   switch(curChar)
   {
      case 69:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x100800000L);
      case 72:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000L);
      case 77:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 79:
         if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 28;
            jjmatchedPos = 3;
         }
         break;
      case 84:
         return jjMoveStringLiteralDfa4_0(active0, 0xc0000000L);
      case 85:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000L);
      case 101:
         if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x100800000L);
      case 104:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000000L);
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000000L);
      case 111:
         if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 28;
            jjmatchedPos = 3;
         }
         break;
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0xc0000000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 3);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 3);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 3);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 67:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
      case 69:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000000L);
      case 73:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000000L);
      case 78:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000000L);
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
      case 101:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000000L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000000L);
      case 199:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000L);
      case 201:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      case 231:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000L);
      case 233:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 4);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 4);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 4);
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa6_0(active0, 0x104000000L);
      case 73:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 77:
         if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 5;
         }
         break;
      case 78:
         return jjMoveStringLiteralDfa6_0(active0, 0x240000000L);
      case 83:
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 5;
         }
         break;
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x104000000L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 109:
         if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 5;
         }
         break;
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x240000000L);
      case 115:
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 5;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(0, 5);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 5);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 5);
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa7_0(active0, 0x100000000L);
      case 65:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000000L);
      case 67:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000L);
      case 79:
         return jjMoveStringLiteralDfa7_0(active0, 0x800000L);
      case 83:
         if ((active0 & 0x4000000L) != 0L)
         {
            jjmatchedKind = 26;
            jjmatchedPos = 6;
         }
         break;
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x200000000L);
      case 99:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000L);
      case 111:
         return jjMoveStringLiteralDfa7_0(active0, 0x800000L);
      case 115:
         if ((active0 & 0x4000000L) != 0L)
         {
            jjmatchedKind = 26;
            jjmatchedPos = 6;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(0, 6);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 6);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 6);
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa8_0(active0, 0x200000000L);
      case 67:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000L);
      case 69:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 7;
         }
         break;
      case 78:
         return jjMoveStringLiteralDfa8_0(active0, 0x800000L);
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000000L);
      case 101:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 7;
         }
         break;
      case 110:
         return jjMoveStringLiteralDfa8_0(active0, 0x800000L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 7);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 7);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 7);
   }
   switch(curChar)
   {
      case 67:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000000L);
      case 69:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 8;
         }
         break;
      case 79:
         return jjMoveStringLiteralDfa9_0(active0, 0x100000000L);
      case 99:
         return jjMoveStringLiteralDfa9_0(active0, 0x200000000L);
      case 101:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 8;
         }
         break;
      case 111:
         return jjMoveStringLiteralDfa9_0(active0, 0x100000000L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 8);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 8);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 8);
   }
   switch(curChar)
   {
      case 77:
         if ((active0 & 0x100000000L) != 0L)
         {
            jjmatchedKind = 32;
            jjmatchedPos = 9;
         }
         break;
      case 79:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000000L);
      case 109:
         if ((active0 & 0x100000000L) != 0L)
         {
            jjmatchedKind = 32;
            jjmatchedPos = 9;
         }
         break;
      case 111:
         return jjMoveStringLiteralDfa10_0(active0, 0x200000000L);
      default :
         break;
   }
   return jjMoveNfa_0(0, 9);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(0, 9);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(0, 9);
   }
   switch(curChar)
   {
      case 77:
         if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 10;
         }
         break;
      case 109:
         if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 10;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(0, 10);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int strKind = jjmatchedKind;
   int strPos = jjmatchedPos;
   int seenUpto;
   input_stream.backup(seenUpto = curPos + 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { throw new Error("Internal Error"); }
   curPos = 0;
   int startsAt = 0;
   jjnewStateCnt = 40;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAddStates(0, 9);
                  }
                  else if ((0x7000000000000000L & l) != 0L)
                  {
                     if (kind > 6)
                        kind = 6;
                  }
                  else if (curChar == 34)
                     jjCheckNAddStates(10, 12);
                  else if (curChar == 46)
                     jjCheckNAdd(7);
                  if (curChar == 60)
                     jjCheckNAddTwoStates(1, 18);
                  else if (curChar == 62)
                     jjCheckNAdd(1);
                  break;
               case 1:
                  if (curChar == 61 && kind > 6)
                     kind = 6;
                  break;
               case 2:
                  if (curChar == 62)
                     jjCheckNAdd(1);
                  break;
               case 4:
                  jjAddStates(13, 14);
                  break;
               case 6:
                  if (curChar == 46)
                     jjCheckNAdd(7);
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddStates(15, 17);
                  break;
               case 9:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(10);
                  break;
               case 10:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(10, 11);
                  break;
               case 12:
                  if (curChar == 34)
                     jjCheckNAddStates(10, 12);
                  break;
               case 13:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(10, 12);
                  break;
               case 15:
                  if ((0x8400000000L & l) != 0L)
                     jjCheckNAddStates(10, 12);
                  break;
               case 16:
                  if (curChar == 34 && kind > 38)
                     kind = 38;
                  break;
               case 17:
                  if (curChar == 60)
                     jjCheckNAddTwoStates(1, 18);
                  break;
               case 18:
                  if (curChar == 62 && kind > 6)
                     kind = 6;
                  break;
               case 19:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAddStates(0, 9);
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(20, 21);
                  break;
               case 21:
                  if (curChar == 47)
                     jjCheckNAdd(22);
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(22, 23);
                  break;
               case 23:
                  if (curChar == 47)
                     jjCheckNAdd(24);
                  break;
               case 24:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  jjCheckNAdd(24);
                  break;
               case 25:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(25, 26);
                  break;
               case 26:
                  if (curChar != 46)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddStates(18, 20);
                  break;
               case 27:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddStates(18, 20);
                  break;
               case 29:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(30);
                  break;
               case 30:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(30, 11);
                  break;
               case 31:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(31, 32);
                  break;
               case 33:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(34);
                  break;
               case 34:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 35)
                     kind = 35;
                  jjCheckNAddTwoStates(34, 11);
                  break;
               case 35:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddStates(21, 23);
                  break;
               case 37:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAdd(38);
                  break;
               case 38:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(38, 11);
                  break;
               case 39:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(39);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if (curChar == 91)
                     jjCheckNAdd(4);
                  break;
               case 4:
                  if ((0xffffffffd7ffffffL & l) != 0L)
                     jjCheckNAddTwoStates(4, 5);
                  break;
               case 5:
                  if (curChar == 93 && kind > 34)
                     kind = 34;
                  break;
               case 8:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(24, 25);
                  break;
               case 11:
                  if ((0x5000000050L & l) != 0L && kind > 35)
                     kind = 35;
                  break;
               case 13:
                  jjCheckNAddStates(10, 12);
                  break;
               case 14:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 15:
                  if ((0x4400010044000L & l) != 0L)
                     jjCheckNAddStates(10, 12);
                  break;
               case 28:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(26, 27);
                  break;
               case 32:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(28, 29);
                  break;
               case 36:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(30, 31);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 4:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(13, 14);
                  break;
               case 13:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(10, 12);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 40 - (jjnewStateCnt = startsAt)))
         break;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { break; }
   }
   if (jjmatchedPos > strPos)
      return curPos;

   int toRet = Math.max(curPos, seenUpto);

   if (curPos < toRet)
      for (i = toRet - Math.min(curPos, seenUpto); i-- > 0; )
         try { curChar = input_stream.readChar(); }
         catch(java.io.IOException e) { throw new Error("Internal Error : Please send a bug report."); }

   if (jjmatchedPos < strPos)
   {
      jjmatchedKind = strKind;
      jjmatchedPos = strPos;
   }
   else if (jjmatchedPos == strPos && jjmatchedKind > strKind)
      jjmatchedKind = strKind;

   return toRet;
}
static final int[] jjnextStates = {
   20, 21, 25, 26, 31, 32, 35, 36, 11, 39, 13, 14, 16, 4, 5, 7, 
   8, 11, 27, 28, 11, 35, 36, 11, 9, 10, 29, 30, 33, 34, 37, 38, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, "\56", "\53", "\55", 
"\52", "\57", "\133", "\135", "\50", "\51", "\54", "\173", "\175", null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x6fffffffc1L, 
};
static final long[] jjtoSkip = {
   0x3eL, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[40];
private final int[] jjstateSet = new int[80];
protected char curChar;
/** Constructor. */
public QueryParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public QueryParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 40; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}

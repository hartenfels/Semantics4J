/**
 * The regular set of Semantics4J tokens.
 *
 * Most of the operators have a normal, Unicode variant and a pure-ASCII,
 * “Texas” variant. They both always result in the same terminal symbols being
 * emitted.
 */
<YYINITIAL> {
  "«"   { dllit_begin( "»" ); }
  "<<<" { dllit_begin(">>>"); }

  "⎨"  { return sym(Terminals.DL_LCURLY); }
  "{|" { return sym(Terminals.DL_LCURLY); }

  "⎬"  { return sym(Terminals.DL_RCURLY); }
  "|}" { return sym(Terminals.DL_RCURLY); }

  "⊤"  { return sym(Terminals.DL_EVERYTHING); }
  "#T" { return sym(Terminals.DL_EVERYTHING); }

  "⊥"  { return sym(Terminals.DL_NOTHING); }
  "#F" { return sym(Terminals.DL_NOTHING); }

  "▽"  { return sym(Terminals.DL_FULL); }
  "#t" { return sym(Terminals.DL_FULL); }

  "△"  { return sym(Terminals.DL_EMPTY); }
  "#f" { return sym(Terminals.DL_EMPTY); }

  "¬"  { return sym(Terminals.DL_NEGATION); }
  "-." { return sym(Terminals.DL_NEGATION); }

  "⁻"  { return sym(Terminals.DL_INVERSION); }
  "^-" { return sym(Terminals.DL_INVERSION); }

  "⊔"   { return sym(Terminals.DL_UNION); }
  "|||" { return sym(Terminals.DL_UNION); }

  "⊓"   { return sym(Terminals.DL_INTERSECTION); }
  "&&&" { return sym(Terminals.DL_INTERSECTION); }

  "∃"  { return sym(Terminals.DL_EXISTENCE); }
  "#E" { return sym(Terminals.DL_EXISTENCE); }

  "∀"  { return sym(Terminals.DL_UNIVERSAL); }
  "#A" { return sym(Terminals.DL_UNIVERSAL); }

  "·"  { return sym(Terminals.DL_DOT); }
  /* The Texas variant for this token is “...”, which is already lexed as the
   * ELLIPSIS terminal. The parser handles the difference between these. */

  "⊔="   { return sym(Terminals.DL_UNIONEQ); }
  "|||=" { return sym(Terminals.DL_UNIONEQ); }

  "⊓="   { return sym(Terminals.DL_INTERSECTIONEQ); }
  "&&&=" { return sym(Terminals.DL_INTERSECTIONEQ); }

  "query-for"   { return sym(Terminals.DL_QUERY);  }
  "switch-type" { return sym(Terminals.DL_SWITCH); }
}


/**
 * Artifical tokens for disambiguating semantic types.
 *
 * The NEVER state is never entered, these tokens are only defined here so that
 * they exist as terminals.
 */
<NEVER> {
  "⊤" { return sym(Terminals.DLT_EVERYTHING); }
  "⊥" { return sym(Terminals.DLT_NOTHING   ); }
  "∃" { return sym(Terminals.DLT_EXISTENCE ); }
  "∀" { return sym(Terminals.DLT_UNIVERSAL ); }
}


/** Recognition of DL literals. */
<DLLIT> {
  /* Everything except a Java line terminator. */
  [^\r\n\u2028\u2029\u000B\u000C\u0085] {
    dllit_buf += str();

    if (dllit_buf.equals(dllit_until)) {
      yybegin(YYINITIAL);
      return sym(Terminals.DL_LITERAL, strbuf.toString(), strlit_start_line,
                 strlit_start_column, strbuf.length() + 2);
    }
    else if (dllit_until.indexOf(dllit_buf) != 0) {
      strbuf.append(dllit_buf);
      dllit_buf = "";
    }
  }

  /* Bail out when no closing delimiter is found. */
  {LineTerminator} { error(String.format("unterminated '%s'", dllit_from)); }
}

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

  "▽"  { return sym(Terminals.DL_ANY); }
  "#t" { return sym(Terminals.DL_ANY); }

  "△"  { return sym(Terminals.DL_NONE); }
  "#f" { return sym(Terminals.DL_NONE); }

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

  "⊔="   { return sym(Terminals.DL_UNIONEQ); }
  "|||=" { return sym(Terminals.DL_UNIONEQ); }

  "⊓="   { return sym(Terminals.DL_INTERSECTIONEQ); }
  "&&&=" { return sym(Terminals.DL_INTERSECTIONEQ); }

  "query-for"   { return sym(Terminals.DL_QUERY);  }
  "switch-type" { return sym(Terminals.DL_SWITCH); }
}


<NEVER> {
  "∃" { return sym(Terminals.DL_TYPE_EXISTENCE); }
  "∀" { return sym(Terminals.DL_TYPE_UNIVERSAL); }
}


<DLLIT> {
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

  {LineTerminator} { error(String.format("unterminated '%s'", dllit_from)); }
}

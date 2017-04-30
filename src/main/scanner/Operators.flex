<YYINITIAL> {
  "«"  { dllit_begin("»"); }
  "<*" { dllit_begin("*>"); }

  "⎨"  { return sym(Terminals.DL_LCURLY); }
  "{*" { return sym(Terminals.DL_LCURLY); }

  "⎬"  { return sym(Terminals.DL_RCURLY); }
  "*}" { return sym(Terminals.DL_RCURLY); }

  "⊤"  { return sym(Terminals.DL_EVERYTHING); }
  "#T" { return sym(Terminals.DL_EVERYTHING); }

  "⊥"  { return sym(Terminals.DL_NOTHING); }
  "#F" { return sym(Terminals.DL_NOTHING); }

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

  "⇒"  { return sym(Terminals.DL_ARROW); }
  "=>" { return sym(Terminals.DL_ARROW); }

  "｢"  { return sym(Terminals.DL_LBRACK); }
  "[*" { return sym(Terminals.DL_LBRACK); }

  "｣"  { return sym(Terminals.DL_RBRACK); }
  "*]" { return sym(Terminals.DL_RBRACK); }
}


<DLLIT> {
  [^\R] {
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

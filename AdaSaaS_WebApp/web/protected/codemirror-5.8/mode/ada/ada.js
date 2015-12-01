// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

(function(mod) {
  if (typeof exports == "object" && typeof module == "object") // CommonJS
    mod(require("../../lib/codemirror"));
  else if (typeof define == "function" && define.amd) // AMD
    define(["../../lib/codemirror"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {
"use strict";

CodeMirror.defineMode("ada", function() {
  function words(str) {
    var obj = {}, words = str.split(" ");
    for (var i = 0; i < words.length; ++i) obj[words[i]] = true;
    return obj;
  }
  function types(str) {
    var obj = {}, types = str.split(" ");
    for (var i = 0; i < types.length; ++i) obj[types[i]] = true;
    return obj;
  }
  var keywords = words("abort abs abstract accept access aliased all and array at begin body " +
    "case constant declare delay delta digits do else elsif end entry exception exit for function " +
    "generic goto if in interface is limited loop mod new not of or others out overriding " +
    "package pragma private procedure protected raise range record rem renames requeue return " +
    "reverse select separate some subtype synchronized tagged task terminate then type until " +
    "use when while with xor");
  var types = words("Access Boolean Character Float Integer Natural Positive String");
  var attributes = words("'Access 'Address 'Ceiling 'Count 'First 'Floor 'Image 'Max 'Min 'Last 'Length 'Range 'Size 'Value 'Width ")
  var atoms = {"null": true};

  var isOperatorChar = /[+\-*&%=<>!?|\/]/;

  function tokenBase(stream, state) {
    var ch = stream.next();
    //if (ch == "#" && state.startOfLine) {
    //  stream.skipToEnd();
    //  return "meta";
    //}
    if (ch=="'") {
      return tokenApostropheKeyword(stream, state);
    }
    if (ch == '"') {
      state.tokenize = tokenString(ch);
      return state.tokenize(stream, state);
    }
    //if (ch == "-" && stream.eat("-")) {
    //  state.tokenize = tokenComment;
    //  return tokenComment(stream, state);
    //}
    if (/[\[\]{}\(\),;\:\.]/.test(ch)) {
      return null;
    }
    if (/\d/.test(ch)) {
      stream.eatWhile(/[\w\.]/);
      return "number";
    }
    if (ch == "-") {
      if (stream.eat("-")) {
        stream.skipToEnd();
        return "comment";
      }
    }
    if (isOperatorChar.test(ch)) {
      stream.eatWhile(isOperatorChar);
      return "operator";
    }
    stream.eatWhile(/[\w\$_]/);
    var cur = stream.current();
    if (keywords.propertyIsEnumerable(cur)) return "keyword"; 
    if (types.propertyIsEnumerable(cur)) return "variable-3";
    if (atoms.propertyIsEnumerable(cur)) return "atom";
    return "variable";
  }

  function tokenString(quote) {
    return function(stream, state) {
      var escaped = false, next, end = false;
      while ((next = stream.next()) != null) {
        if (next == quote && !escaped) {end = true; break;}
        escaped = !escaped && next == "\\";
      }
      if (end || !escaped) state.tokenize = null;
      return "string";
    };
  }
  function tokenApostropheKeyword(stream,state) {
    var maybeEnd = false, ch, ch2;
    ch = stream.next();
    ch2= stream.next();
    if (ch2=="'") {
      console.log("Found char");
      return "string";
    }
    else {
      stream.eatWhile(/[\w\$_]/);
      var cur = stream.current();
      console.log("Search attribute with");
      console.log(cur);
      if (attributes.propertyIsEnumerable(cur)) return "keyword";
      else return null;
    }
  }
  function tokenComment(stream, state) {
    var maybeEnd = false, ch;
    while (ch = stream.next()) {
      if (ch == "\n") {
        state.tokenize = null;
        break;
      }
    }
    return "comment";
  }

  // Interface

  return {
    startState: function() {
      return {tokenize: null};
    },

    token: function(stream, state) {
      if (stream.eatSpace()) return null;
      var style = (state.tokenize || tokenBase)(stream, state);
      if (style == "comment" || style == "meta") return style;
      return style;
    },

    electricChars: "{}"
  };
});

CodeMirror.defineMIME("text/x-ada", "ada");

});

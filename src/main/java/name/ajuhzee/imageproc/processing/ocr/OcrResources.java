package name.ajuhzee.imageproc.processing.ocr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides recources for teaching a character set.
 * 
 * @author Ajuhzee
 *
 */
public final class OcrResources {

	/**
	 * A map that contains a distinct caption for each characters.
	 */
	public static final Map<String, Character> CHARACTER_NAMES;
	static {
		Map<String, Character> characterNames = new HashMap<>();
		characterNames.put("CAPITAL_A", 'A');
		characterNames.put("CAPITAL_B", 'B');
		characterNames.put("CAPITAL_C", 'C');
		characterNames.put("CAPITAL_D", 'D');
		characterNames.put("CAPITAL_E", 'E');
		characterNames.put("CAPITAL_F", 'F');
		characterNames.put("CAPITAL_G", 'G');
		characterNames.put("CAPITAL_H", 'H');
		characterNames.put("CAPITAL_I", 'I');
		characterNames.put("CAPITAL_J", 'J');
		characterNames.put("CAPITAL_K", 'K');
		characterNames.put("CAPITAL_L", 'L');
		characterNames.put("CAPITAL_M", 'M');
		characterNames.put("CAPITAL_N", 'N');
		characterNames.put("CAPITAL_O", 'O');
		characterNames.put("CAPITAL_P", 'P');
		characterNames.put("CAPITAL_Q", 'Q');
		characterNames.put("CAPITAL_R", 'R');
		characterNames.put("CAPITAL_S", 'S');
		characterNames.put("CAPITAL_T", 'T');
		characterNames.put("CAPITAL_U", 'U');
		characterNames.put("CAPITAL_V", 'V');
		characterNames.put("CAPITAL_W", 'W');
		characterNames.put("CAPITAL_X", 'X');
		characterNames.put("CAPITAL_Y", 'Y');
		characterNames.put("CAPITAL_Z", 'Z');
		characterNames.put("a", 'a');
		characterNames.put("b", 'b');
		characterNames.put("c", 'c');
		characterNames.put("d", 'd');
		characterNames.put("e", 'e');
		characterNames.put("f", 'f');
		characterNames.put("g", 'g');
		characterNames.put("h", 'h');
		characterNames.put("i", 'i');
		characterNames.put("j", 'j');
		characterNames.put("k", 'k');
		characterNames.put("l", 'l');
		characterNames.put("m", 'm');
		characterNames.put("n", 'n');
		characterNames.put("o", 'o');
		characterNames.put("p", 'p');
		characterNames.put("q", 'q');
		characterNames.put("r", 'r');
		characterNames.put("s", 's');
		characterNames.put("t", 't');
		characterNames.put("u", 'u');
		characterNames.put("v", 'v');
		characterNames.put("w", 'w');
		characterNames.put("x", 'x');
		characterNames.put("y", 'y');
		characterNames.put("z", 'z');
		characterNames.put("0", '0');
		characterNames.put("1", '1');
		characterNames.put("2", '2');
		characterNames.put("3", '3');
		characterNames.put("4", '4');
		characterNames.put("5", '5');
		characterNames.put("6", '6');
		characterNames.put("7", '7');
		characterNames.put("8", '8');
		characterNames.put("9", '9');
		characterNames.put("exclamation_mark", '!');
		characterNames.put("at", '@');
		characterNames.put("hash", '#');
		characterNames.put("dollar", '$');
		characterNames.put("percent", '%');
		characterNames.put("caret", '^');
		characterNames.put("ampersand", '&');
		characterNames.put("star", '*');
		characterNames.put("parenthesis_open", '(');
		characterNames.put("parenthesis_close", ')');
		characterNames.put("underscore", '_');
		characterNames.put("plus", '+');
		characterNames.put("minus", '-');
		characterNames.put("equals", '=');
		characterNames.put("bar", '|');
		characterNames.put("backslash", '\\');
		characterNames.put("curly_brace_open", '{');
		characterNames.put("curly_brace_close", '}');
		characterNames.put("square_bracket_open", '[');
		characterNames.put("square_bracket_close", ']');
		characterNames.put("colon", ':');
		characterNames.put("quote", '"');
		characterNames.put("tilde", '~');
		characterNames.put("less", '<');
		characterNames.put("greater", '>');
		characterNames.put("question_mark", '?');
		characterNames.put("semicolon", ';');
		characterNames.put("single_quote", '\'');
		characterNames.put("comma", ',');
		characterNames.put("dot", '.');
		characterNames.put("slash", '/');
		CHARACTER_NAMES = characterNames;
	}

	/**
	 * A list that contains the character learning order with the character captions.
	 */
	public static final List<String> CHARACTER_LEARNING_ORDER;
	static {
		List<String> characterOrder = new ArrayList<>();
		characterOrder.add("CAPITAL_A");
		characterOrder.add("CAPITAL_B");
		characterOrder.add("CAPITAL_C");
		characterOrder.add("CAPITAL_D");
		characterOrder.add("CAPITAL_E");
		characterOrder.add("CAPITAL_F");
		characterOrder.add("CAPITAL_G");
		characterOrder.add("CAPITAL_H");
		characterOrder.add("CAPITAL_I");
		characterOrder.add("CAPITAL_J");
		characterOrder.add("CAPITAL_K");
		characterOrder.add("CAPITAL_L");
		characterOrder.add("CAPITAL_M");
		characterOrder.add("CAPITAL_N");
		characterOrder.add("CAPITAL_O");
		characterOrder.add("CAPITAL_P");
		characterOrder.add("CAPITAL_Q");
		characterOrder.add("CAPITAL_R");
		characterOrder.add("CAPITAL_S");
		characterOrder.add("CAPITAL_T");
		characterOrder.add("CAPITAL_U");
		characterOrder.add("CAPITAL_V");
		characterOrder.add("CAPITAL_W");
		characterOrder.add("CAPITAL_X");
		characterOrder.add("CAPITAL_Y");
		characterOrder.add("CAPITAL_Z");
		characterOrder.add("a");
		characterOrder.add("b");
		characterOrder.add("c");
		characterOrder.add("d");
		characterOrder.add("e");
		characterOrder.add("f");
		characterOrder.add("g");
		characterOrder.add("h");
		characterOrder.add("i");
		characterOrder.add("j");
		characterOrder.add("k");
		characterOrder.add("l");
		characterOrder.add("m");
		characterOrder.add("n");
		characterOrder.add("o");
		characterOrder.add("p");
		characterOrder.add("q");
		characterOrder.add("r");
		characterOrder.add("s");
		characterOrder.add("t");
		characterOrder.add("u");
		characterOrder.add("v");
		characterOrder.add("w");
		characterOrder.add("x");
		characterOrder.add("y");
		characterOrder.add("z");
		characterOrder.add("0");
		characterOrder.add("1");
		characterOrder.add("2");
		characterOrder.add("3");
		characterOrder.add("4");
		characterOrder.add("5");
		characterOrder.add("6");
		characterOrder.add("7");
		characterOrder.add("8");
		characterOrder.add("9");
		characterOrder.add("exclamation_mark");
		characterOrder.add("at");
		characterOrder.add("hash");
		characterOrder.add("dollar");
		characterOrder.add("percent");
		characterOrder.add("caret");
		characterOrder.add("ampersand");
		characterOrder.add("star");
		characterOrder.add("parenthesis_open");
		characterOrder.add("parenthesis_close");
		characterOrder.add("underscore");
		characterOrder.add("plus");
		characterOrder.add("minus");
		characterOrder.add("equals");
		characterOrder.add("bar");
		characterOrder.add("backslash");
		characterOrder.add("curly_brace_open");
		characterOrder.add("curly_brace_close");
		characterOrder.add("square_bracket_open");
		characterOrder.add("square_bracket_close");
		characterOrder.add("colon");
		characterOrder.add("quote");
		characterOrder.add("tilde");
		characterOrder.add("less");
		characterOrder.add("greater");
		characterOrder.add("question_mark");
		characterOrder.add("semicolon");
		characterOrder.add("single_quote");
		characterOrder.add("comma");
		characterOrder.add("dot");
		characterOrder.add("slash");
		CHARACTER_LEARNING_ORDER = characterOrder;
	}

}

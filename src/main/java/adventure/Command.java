package adventure;

public final class Command{
    private String action;
    private String noun;
    private static final String[] ALL_COMMANDS = new String[]{"inventory","quit","look","go","take",
            "eat","wear","toss","read"};
  /**
     * Create a command object with default values.  
     * both instance variables are set to null
     * 
     */
    public Command() throws InvalidCommandException {
        this(null, null);
    }



  /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * 
     */
    public Command(String command) throws InvalidCommandException{

        this(command, null);
        }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what      The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{

        if(!validCommand(command)) {
            throw new InvalidCommandException();
        }

        this.action = command;
        this.noun = what;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }

    public static String[] getAllCommands() {
        return ALL_COMMANDS;
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (noun != null);
    }

    /**
     * A method that evaluates whether the actionword of a command
     * is valid by looping through a static list of available actionwords
     * @param command Actionword to be evalued, usually from a Scanner.
     * @return boolean whether the actionword of the command is valid
     */
    public boolean validCommand(String command){
        for (int i = 0; i < ALL_COMMANDS.length; i++) {
            if(command.equals(ALL_COMMANDS[i])) {
                return true;
            }

        }
        return false;
    }

    /**
     * Sets the String action parameter of the command
     * @param aAction String; the action from the user
     */
    public void setAction(String aAction) {
        action = aAction;
    }

    public void setNoun(String aNoun) {
        noun = aNoun;
    }

    @Override
    public String toString() {
        return "Command{"
                +"action='" + action + '\''
                + ", noun='" + noun + '\''
                + '}';
    }
}

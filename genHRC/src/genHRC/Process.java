package genHRC;

public class Process {

	
	private String reference;
	private String command;
	private String configFile;
	private String input;
	private String output;
	private String inputExt;
	private String outputExt;
	private String description;
	
	
	/** Process constructor retrieving all its attributes from the database
	 * @param reference
	 */
	public Process(String reference)
	{
		Connection connection = Connection.getInstance();
		this.reference = reference;
		this.command = getDBCommand(connection);
		this.configFile = getDBConfigFile(connection);
		this.input = getDBInput(connection);
		this.inputExt = getDBInputExt(connection);
		this.output = getDBOutput(connection);
		this.outputExt = getDBOutputExt(connection);
		this.description = getDBDescription(connection);
	}
	
	/*
	 * Methods retrieving a specific attribute from the database
	 */
	
	/**
	 * @param connection
	 * @return Command
	 */
	public String getDBCommand(Connection connection) {
		return connection.getCommand(this.reference);
	}
	/**
	 * @param connection
	 * @return ConfigFile
	 */
	public String getDBConfigFile(Connection connection) {
		return connection.getConfigFile(this.reference);
	}
	/**
	 * @param connection
	 * @return Input
	 */
	public String getDBInput(Connection connection) {
		return connection.getInput(this.reference);
	}
	/**
	 * @param connection
	 * @return Output
	 */
	public String getDBOutput(Connection connection) {
		return connection.getOutput(this.reference);
	}
	/**
	 * @param connection
	 * @return InputExt
	 */
	public String getDBInputExt(Connection connection) {
		return connection.getInputExt(reference);
	}
	/**
	 * @param connection
	 * @return OutputExt
	 */
	public String getDBOutputExt(Connection connection) {
		return connection.getOutputExt(reference);
	}
	/**
	 * @param connection
	 * @return Description
	 */
	public String getDBDescription(Connection connection) {
		return connection.getDescription(this.reference);
	}
	
	
	/*
	 * Getters for the class used to access the attributes retrieved from the database
	 */
	
	/**
	 * @return reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @return configFile
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * @return input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @return output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @return inputExt
	 */
	public String getInputExt() {
		return inputExt;
	}

	/**
	 * @return outputExt
	 */
	public String getOutputExt() {
		return outputExt;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	
}


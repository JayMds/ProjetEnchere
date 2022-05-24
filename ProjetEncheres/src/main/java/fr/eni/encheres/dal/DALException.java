package fr.eni.encheres.dal;

public class DALException extends Exception {

		// Chaînage des Exceptions
		public DALException(String message) {
			super(message);
		}
		public DALException(Throwable cause ) {
			super(cause);
		}
	}



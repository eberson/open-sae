package br.org.sae.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

@SuppressWarnings("rawtypes")
public class SaeValidationException extends ValidationException{
	
	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation> violations;
	
	public SaeValidationException(Set<ConstraintViolation> violations) {
		super();
		this.violations = violations;
	}

	public Set<ConstraintViolation> getViolations() {
		return violations;
	}
	
}

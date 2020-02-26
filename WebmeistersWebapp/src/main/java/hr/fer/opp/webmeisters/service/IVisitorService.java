package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.data.form.VisitorForm;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;

public interface IVisitorService {
	public Visitor registerNewVisitorAccount(VisitorForm visitorForm) throws EmailExistsException;
}

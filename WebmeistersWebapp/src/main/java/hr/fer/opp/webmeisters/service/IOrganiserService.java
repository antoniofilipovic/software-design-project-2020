package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.data.form.EditInfoOrganiserForm;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.exception.EmailExistsException;

public interface IOrganiserService {
    public Organiser registerNewOrganiserAccount(OrganiserForm visitorForm) throws EmailExistsException;

    public Organiser updateOrganiserAccount(Organiser oldOrg, EditInfoOrganiserForm organiserForm) throws  EmailExistsException;

    public Organiser deleteOrganiserAccount(Organiser org);

   // public Organiser findOrganiser(String mail);
}

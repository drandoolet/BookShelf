package bookshelf.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorFactory {
    private BookShelfService service;

    @Autowired
    public AuthorFactory(BookShelfService shelfService) {
        this.service = shelfService;
    }

    public AuthorFactory() {
    }


}

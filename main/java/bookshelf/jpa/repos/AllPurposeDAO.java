package bookshelf.jpa.repos;

public class AllPurposeDAO<T> {
    private Class<T> entityClass;

    public AllPurposeDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // TODO wire PersistenceContext & provide impls
}

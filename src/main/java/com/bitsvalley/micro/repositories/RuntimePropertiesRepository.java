package com.bitsvalley.micro.repositories;
        import com.bitsvalley.micro.domain.RuntimeProperties;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;

        import java.util.List;

public interface RuntimePropertiesRepository extends CrudRepository<RuntimeProperties, Long> {

    @Query(value = "SELECT * FROM runtimeproperties rp where rp.property_name = :propName and rp.org_id = :orgId", nativeQuery = true)
    public RuntimeProperties findByPropertyNameAndOrgId(String propName, long orgId);
    public Iterable<RuntimeProperties> findByOrgId(long orgId);
    @Query(value = "SELECT * FROM runtimeproperties rp where rp.property_name = :propName and rp.property_value = :bidValue", nativeQuery = true)
    public RuntimeProperties findByBid(String propName, String bidValue);
}

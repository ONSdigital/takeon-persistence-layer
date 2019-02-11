package uk.gov.ons.collection.PersistenceLayer.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ons.collection.PersistenceLayer.utilities.MapConverter;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntityShort;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorShortRepo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Iterables.toArray;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


//Declare the class as a controller class
@Api(value = "Contributor Entity", description = "offers CRUD operations for the Contributor entity")
@RestController
@RequestMapping(value = "/contributor")
public class ContributorController {

    private List<String> validTextColumns = new ArrayList<>(Arrays.asList("reference", "period", "survey", "status"));

    private int pageNumber = 1;
    private int startRow = 0;
    private double pageSizeD = 10;
    private int pageSize = (int) pageSizeD;

    //Use the autowired annotation. This automatically passes the values to the repo
    @Autowired
    //Create the object
    private uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo ContribRepo;

    @Autowired
    private ContributorShortRepo ConShortRepo;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private EntityLinks links;


    //Bind the return to the current webpage
    @ApiOperation(value = "Get list of links", response = String.class)
    @GetMapping("/help")
    public String apiHelp() {
        String outputString = "";
        outputString += "<ul>";
        outputString += "<li><a href='/contributor/findByConPrimaryKey'>Find by Primary Key -> returns JSON!</a></li>";
        outputString += "<li><a href='/contributor/search'>Select all -> returns JSON!</a></li>";
        outputString += "<li><a href='/contributor/search/{parameters}'>Select all Where condition 1 AND condition 2 etc... -> returns JSON!</a></li>";
        return outputString;
    }

    @ApiOperation(value = "Find a contributor by passing {PrimaryKey}", notes = "The 3 primary key values are required")
    @GetMapping(value = "/searchByPrimaryKey/{pk}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "Contributor with given Primary Key does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public Optional<ContributorEntity> searchByPrimaryKey(@ApiParam(value = "Reference number", required=true) @MatrixVariable String reference,
                                                           @ApiParam(value = "Selection period", required=true) @MatrixVariable String period,
                                                           @ApiParam(value = "Survey ID", required=true) @MatrixVariable String survey) {

        return this.ContribRepo.findById(new ContributorKey(reference, period, survey));

    }

    /* Disabled until pagination is needed
    @ApiOperation(value = "Returns all Contributors", notes = "Returns all columns for all Contributors")
    @GetMapping(value = "/searchAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public Iterable<ContributorEntity> getAll() {
        return this.ContribRepo.findAll();
    }
    */

    @ApiOperation(value = "Find a contributor by passing any number of search parameters", notes = "Parameters are required for the search")
    @GetMapping(value = "/search/{vars}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist for given search parameters"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public List getSearch(@MatrixVariable Map<String, String> matrixVars) {

    String search_string = new String();
    search_string = " 1=1";

    EntityManager entityManager = emf.createEntityManager();

    Set set = matrixVars.entrySet();
    Iterator iterator = set.iterator();
    while(iterator.hasNext()) {
      Map.Entry mentry = (Map.Entry)iterator.next();

        // Can change the = to operator - take it from URL
        search_string += " AND " + mentry.getKey() + " LIKE " + "'%" + mentry.getValue() + "%'";
        }


        javax.persistence.Query query = entityManager.createQuery("select a from ContributorEntity a where " + search_string);

    return query.getResultList();

    }

    // using hibernate criteria builder and predicates
    @ApiOperation(value = "Find a contributor by passing any number of search parameters", notes = "Parameters are required for the search")
    @GetMapping(value = "/searchBy/{searchVars}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist for given search parameters"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public Iterable<ContributorEntity> searchBy(@MatrixVariable(required = true) Map<String, String> matrixVars) {

        CriteriaBuilder builder = emf.getCriteriaBuilder();
        CriteriaQuery<ContributorEntity> query = builder.createQuery(ContributorEntity.class);
        Root<ContributorEntity> root = query.from(ContributorEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            predicates.add(builder.equal(root.get(fieldName), value));
        }

        List<ContributorEntity> contributors = new ArrayList<>();

        if (predicates.size()>0) {
            query.where(toArray(predicates, Predicate.class));
            contributors = emf.createEntityManager().createQuery(query.select(root)).getResultList();
        }

        return contributors;
    }

    // using hibernate criteria builder and predicates
    @ApiOperation(value = "Find a contributor by passing any number of search parameters (LIKE)", notes = "Parameters are required for the search")
    @GetMapping(value = "/searchByLike/{searchVars}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist for given search parameters"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public Iterable<ContributorEntityShort> searchByLike(@MatrixVariable Map<String, String> matrixVars) {
        EntityManager entityManager = emf.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContributorEntityShort> query = builder.createQuery(ContributorEntityShort.class);
        Root<ContributorEntityShort> root = query.from(ContributorEntityShort.class);


        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (validTextColumns.contains(fieldName)) {
                predicates.add(builder.like(root.get(fieldName), "%" +  value + "%"));
            }
            else {
                predicates.add(builder.equal(root.get(fieldName), value));
            }
        }

        List<ContributorEntityShort> contributors = new ArrayList<>();

        if (predicates.size()>0) {
            query.where(toArray(predicates, Predicate.class));
            contributors = entityManager.createQuery(query.select(root)).getResultList();
        }
        entityManager.close();
        return contributors;

    }

    @ApiOperation(value = "Find a contributor by passing any number of search parameters (LIKE)", notes = "Parameters are required for the search")
    @GetMapping(value = "/searchByLikePage/{searchVars}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist for given search parameters"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public Iterable<ContributorEntityShort> searchByLikePage(@MatrixVariable Map<String, String> matrixVars) {

        EntityManager entityManager = emf.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Query using matrixVars
        CriteriaQuery<ContributorEntityShort> criteriaQuery = criteriaBuilder
                .createQuery(ContributorEntityShort.class);
        Root<ContributorEntityShort> root = criteriaQuery.from(ContributorEntityShort.class);

        if (matrixVars.containsKey("sort")) {
            if (matrixVars.containsKey("order") && matrixVars.get("order").equalsIgnoreCase("DESC")){
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(matrixVars.get("sort"))));
            }
            else {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(matrixVars.get("sort"))));
            }
        }


        if (matrixVars.containsKey("page")) {
            if ((Integer.parseInt(matrixVars.get("page"))) > 0) {
                pageNumber = Integer.parseInt(matrixVars.get("page"));
            }
        }

        // Remove sort and order from Matrix Vars so it's not used in Where clause / Predicates
        matrixVars.remove("sort");
        matrixVars.remove("order");
        matrixVars.remove("page");

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if (validTextColumns.contains(fieldName)) {
                predicates.add(criteriaBuilder.like(root.get(fieldName), "%" +  value + "%"));
            }
            else {
                predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
            }
        }

        if (predicates.size() > 0) {
            criteriaQuery.where(toArray(predicates, Predicate.class));
        }

        // Get count of entities - can't reuse Predicates as it causes issues
        String search_string = new String();
        search_string = " 1=1";

        Set set = matrixVars.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();

            search_string += " AND " + mentry.getKey() + " LIKE " + "'%" + mentry.getValue() + "%'";
        }

        Query queryTotal = entityManager.createQuery
                ("Select count(a) from ContributorEntity a where " + search_string);
        long count = (long)queryTotal.getSingleResult();

        TypedQuery<ContributorEntityShort> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

        List<ContributorEntityShort> contributors = new ArrayList<>();

        // Calculate Last Page
        double lastPageCalc = (count / pageSizeD);

        System.out.println("Count: " + count);
        System.out.println("Page Size: " + pageSizeD);
        System.out.println("Last Page Calc: " + lastPageCalc);

        int lastPage = (int) Math.ceil(lastPageCalc);
        System.out.println("Last Page: " + lastPage);

        if (pageNumber <= lastPage) {
            startRow = ((pageNumber * pageSize) - pageSize);
            System.out.println("Start Row: " + startRow);
            typedQuery.setFirstResult(startRow);
            typedQuery.setMaxResults(pageSize);
            contributors = typedQuery.getResultList();
        }

        return contributors;

    }


    @ApiOperation(value = "Find a contributor by passing any number of search parameters (LIKE)", notes = "Parameters are required for the search")
    @GetMapping(value = "/searchByLikePageable/{searchVars}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of Contributor details", response = ContributorEntity.class),
            @ApiResponse(code = 404, message = "No Contributors exist for given search parameters"),
            @ApiResponse(code = 500, message = "Internal server error")}
    )
    public ResponseEntity<PagedResources<ContributorEntityShort>> searchByLikePageable(PagedResourcesAssembler assembler, @MatrixVariable Map<String, String> matrixVars,
                                                                             @RequestParam("page") int page, @RequestParam("size") int size,
                                                                             @RequestParam("sort") String sort) {

        String direction = new String();
        String field = new String();
        Pattern pattern = Pattern.compile(", *");
        Matcher matcher = pattern.matcher(sort);
        if (matcher.find()) {
            field = sort.substring(0, matcher.start());
            direction = sort.substring(matcher.end());
        }

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.fromString(direction), field);

        Specification<ContributorEntityShort> contributorEntitySpecification = new Specification<ContributorEntityShort>() {
            @Override
            public Predicate toPredicate(Root<ContributorEntityShort> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
                    String fieldName = entry.getKey();
                    String value = entry.getValue();
                    if (validTextColumns.contains(fieldName)) {
                        predicates.add(criteriaBuilder.like(root.get(fieldName), "%" +  value + "%"));
                    }
                    else {
                        predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
                    }
                }
                return criteriaBuilder.and(toArray(predicates, Predicate.class));
            }
        };

        MapConverter converter = new MapConverter();
        String search_string = converter.mapToString(matrixVars);

        Page<ContributorEntityShort> pageData = ConShortRepo.findAll(contributorEntitySpecification,pageable);
        // withSelfRel() builds the links
        PagedResources<ContributorEntityShort> pr = assembler.toResource(pageData, linkTo(ContributorController.class).slash("/searchByLikePageable" + "/" + search_string).withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", createLinkHeader(pr));
        return new ResponseEntity <> (assembler.toResource(pageData, linkTo(ContributorController.class).slash("/searchByLikePageable" + "/" + search_string).withSelfRel()), responseHeaders, HttpStatus.OK);
    }

    private String createLinkHeader(PagedResources<ContributorEntityShort> pr) {
        final StringBuilder linkHeader = new StringBuilder();
        try {
            linkHeader.append(buildLinkHeader(pr.getLinks("first").get(0).getHref(), "first"));
            linkHeader.append(", ");
            linkHeader.append(buildLinkHeader(pr.getLinks("next").get(0).getHref(), "next"));
            linkHeader.append(", ");
            linkHeader.append(buildLinkHeader(pr.getLinks("prev").get(0).getHref(), "prev"));
            linkHeader.append(", ");
            linkHeader.append(buildLinkHeader(pr.getLinks("last").get(0).getHref(), "last"));
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Can't create header links as there is no data: " + error.toString());
        }

        return linkHeader.toString();
    }

    public static String buildLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }
}

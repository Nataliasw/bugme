package com.finalproject.bugme.issue;

import com.finalproject.bugme.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueFilter {

    private String name;

    private Person assignee;

    private Person creator;

    private String globalSearch;


    public Specification<Issue> buildQuery() {
        return Specification.anyOf(
                ilike("name", globalSearch),
                ilike("description", globalSearch)
        ).and(
                Specification.anyOf(
                        ilike("name", name),
                        equalTo("creator",creator),
                        equalTo("assignee",assignee)

                )
        );
    }


    private Specification<Issue> equalTo(String property, Object value) {
        if (value == null) {
            return Specification.where(null);
        }
        return ((root, query, builder) -> builder.equal(root.get(property), value));
    }

    private Specification<Issue> ilike(String property, String value) {
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, builder) -> builder.like(builder.lower(root.get(property)), "%" + value.toLowerCase() + "%");
    }

    public String toQueryString(Integer page) {
        return "page=" + page + (name != null ? "&name=" + name : "") +
                (assignee != null ? "&assignee=" + assignee.getId() : "") +
                (creator != null ? "&creator=" + creator.getId() : "") +
                (globalSearch != null ? "&globalSearch" + globalSearch : "");
    }

}

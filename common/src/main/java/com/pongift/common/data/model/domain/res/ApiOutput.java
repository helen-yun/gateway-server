package com.pongift.common.data.model.domain.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiOutput {
	int resultCode;
	JsonNode data;
	String message;
	Response response;
	String error;
	ArrayList<LinkedHashMap> list;
}

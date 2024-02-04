package com.task05;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.task05.dto.Request;
import com.task05.dto.Response;
import com.task05.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@LambdaHandler(lambdaName = "api_handler",
	roleName = "api_handler-role"
)
public class ApiHandler implements RequestHandler<Request, Response> {
	private static final String AWS_REGION = "eu-central-1";
	private static final String CREATED_STATUS_CD = "201";

	public Response handleRequest(Request request, Context context) {
		final Event event = buildEvent(request);

		processEvent(event, context);

		return buildResponse(event);
	}

	private Event buildEvent(final Request request)
	{
		final DateTimeFormatter formatter =
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");

		return Event.builder()
				.id(UUID.randomUUID().toString())
				.principalId(request.getPrincipalId())
				.createdAt(LocalDateTime.now().format(formatter))
				.body(request.getContent())
				.build();
	}

	private void processEvent(final Event event, final Context context)
	{
		final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withRegion(AWS_REGION)
				.build();
		final DynamoDBMapper dbMapper = new DynamoDBMapper(amazonDynamoDB);
		final LambdaLogger logger = context.getLogger();

		dbMapper.save(event);

		logger.log(String.format("Event processed!\nEvent: %s", event.toString()));
	}

	private Response buildResponse(final Event event)
	{
		return Response.builder()
				.statusCode(CREATED_STATUS_CD)
				.event(event)
				.build();
	}
}

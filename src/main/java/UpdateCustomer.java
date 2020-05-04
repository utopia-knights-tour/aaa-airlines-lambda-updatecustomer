
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import entity.Customer;
import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;
import service.AgentService;

public class UpdateCustomer implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	private AgentService agentService = new AgentService();

	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		LambdaLogger logger = context.getLogger();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Access-Control-Allow-Origin", "*");
		try {
			Customer customer = new Gson().fromJson(request.getBody(), Customer.class);
			if (customer == null || customer.getCustomerId() == null || customer.getCustomerName() == null
					|| customer.getCustomerAddress() == null || customer.getCustomerPhone() == null) {
				return new ApiGatewayProxyResponse(400, headers, null);
			}
			boolean updateSucceeded = agentService.updateCustomer(customer);
			if (!updateSucceeded) {
				return new ApiGatewayProxyResponse(400, headers, null);
			}
			return new ApiGatewayProxyResponse(204, headers, null);
		} catch (JsonParseException | SQLException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(400, headers, null);
		} catch (ClassNotFoundException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(500, headers, null);
		}
	}
}
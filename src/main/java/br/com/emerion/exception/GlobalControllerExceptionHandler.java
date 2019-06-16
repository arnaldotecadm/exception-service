package br.com.emerion.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private Log logger = LogFactory.getLog(GlobalControllerExceptionHandler.class);
	
	@ExceptionHandler({ ConversionFailedException.class })
	@ResponseBody
	public ResponseEntity<String> handleCustomException(ConversionFailedException ex) {
		logger.error(ex.getMessage());
		logger.error(ex);
		if (ex.getMessage().contains("No enum constant")) {
			return new ResponseEntity<>(
					"Valor informado para o par�metro n�o compat�vel com o valor informado, por favor consulte a documenta��o para maiores detalhes.",
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ServiceException.class })
	@ResponseBody
	public ResponseEntity<ServiceExceptionDTO> handleVersioningException(ServiceException ve) {
		logger.error(ve.getMessage());
		logger.error(ve);
		return new ResponseEntity<>(ve.getExceptionDTO(), ve.getHttpStatus());
	}
}
package ${package}.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(Constants.CDI_PREFIX + "eIDController")
public class BelgianIdentityCardController {

	public void createPhoto(OutputStream outputStream, Object data)
			throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		byte[] photo = (byte[]) sessionMap.get("eid.photo");
		outputStream.write(photo);
	}
}

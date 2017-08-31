/*******************************************************************************
 * (c) Copyright IBM Corporation 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package wasdev.secureejb.ejb.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wasdev.secureejb.ejb.SampleSecureStatelessBean;

/**
 * A servlet protected by the role "servletRole"
 * which injects a stateless EJB
 */
@WebServlet({ "/sampleServlet", "/" })
@ServletSecurity(@HttpConstraint(rolesAllowed = "servletRole"))
public class SecureEJBServlet extends HttpServlet {

    /**  */
    private static final long serialVersionUID = -711204607808974874L;
    @EJB
    SampleSecureStatelessBean statelessBean;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        StringBuffer sb = new StringBuffer();
        // Call hello method on a stateless session bean
        try {
            String message = statelessBean.hello();
            writer.println("In SecureEJBServlet, " + message);
        } catch (Throwable t) {
            t.printStackTrace(writer);
        }
    }
}
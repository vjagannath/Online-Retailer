import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutoCompleteServlet extends HttpServlet
{
	private ServletContext context;

	public void init(ServletConfig config) throws ServletException
	{
		this.context = config.getServletContext();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
        String targetId = request.getParameter("id");

        // Check if any user has logged in
        boolean userHasLoggedIn = false;
        HttpSession session = request.getSession();
        String loggedInFor = (String)session.getAttribute("userIdForOrder");
        if(loggedInFor != null)
        {
        	userHasLoggedIn = true;
        }

        StringBuffer sb = new StringBuffer();

        if (targetId != null)
        {
            targetId = targetId.trim().toLowerCase();
        }
        else
        {
            context.getRequestDispatcher("/Error.jsp").forward(request, response);
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {

            // check if user sent empty string
            if (!targetId.equals(""))
			{
				AjaxUtility utility = new AjaxUtility();
				sb = utility.readData(targetId);
				if(sb != null && !sb.equals(""))
				{
					namesAdded = true;
				}
			}

			if(namesAdded)
			{
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("<composers>" + sb.toString() + "</composers>");
			}
			else
			{
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
		}
		if(action.equals("lookup"))
		{
			if(targetId!=null)
			{
				AjaxUtility utility = new AjaxUtility();
				ProductAccessory product = utility.getData(targetId);
				if(product != null)
				{
					request.setAttribute("composer", product);
					if(userHasLoggedIn)
					{
						context.getRequestDispatcher("/ComposerForUser.jsp").forward(request, response);
					}
					else
					{
						context.getRequestDispatcher("/Composer.jsp").forward(request, response);
					}
				}
			}
		}
	}
}

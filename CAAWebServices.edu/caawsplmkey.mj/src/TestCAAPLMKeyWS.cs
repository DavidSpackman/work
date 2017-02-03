/** 
 * This program may be used, executed, copied, modified and distributed
 * without royalty for the purpose of developing, using, marketing, or distributing.
 * 
 **/

using System;
using System.Threading;
using www._3ds.com.wserror;


public partial class TestCAAPLMKeyClient
{
  int _validityInMn = 1;
  string _adminKey = null;
  string _url = null;
  string _userName = "fooUser";
  string _pwdName = "XXXXXX";
  string _productName = "CATIA V5";
  string _companyName = "DASSAULT SYSTEMES";
 

  /**
   * Main
   * main program entry point
   * 
   * @param args - string[]
   */
  static void Main(string[] args)
  {
    TestCAAPLMKeyClient ws = new TestCAAPLMKeyClient();
    if (ws.ParseArgs(args))
    {
      ws.PrintUsage();
      return;
    }
    else
    {
      ws.CallService();
    }
  }

  /**
   * ParseArgs
   * Read the program options from the command line
   * 
   * @param args - string[] - Same as main
   * @return - bool - true if there is an error that requires help
   */
  bool ParseArgs(string[] args)
  {
    bool isError = false;
    if (args.Length > 0)
    {

      for (int argc = 0; argc < args.Length; argc++)
      {
        try
        {
          if (args[argc][0] == '-')
          { // Options require '-'
            switch (args[argc][1])
            {
              case 'u':
              case 'U':
                _url = args[++argc];
                break;
              case 'n':
              case 'N':
                _userName = args[++argc];
                break;
              case 'p':
              case 'P':
                _pwdName = args[++argc];
                break;
              case 'a':
              case 'A':
                _productName = args[++argc];
                break;
              case 'c':
              case 'C':
                _companyName = args[++argc];
                break;
              case 'm':
              case 'M':
                _validityInMn = Int32.Parse(args[++argc]);
                break;
              case 'k':
              case 'K':
                _adminKey = args[++argc];
                break;
              
            }
          }
          else
          { // No '-'. 
            isError = true;
            Console.WriteLine("Unrecognized Parameter: " + args[argc]);
          }
        }
        catch
        {  // Could be a missing option value
          isError = true;
          Console.WriteLine("Incorrect Parameter Format");
          break; // the For loop
        }

      }

    }
    if (_url == null || _adminKey == null)
    {
      Console.WriteLine("Mandatory Argument Missing");
      isError = true;
    }
    _url += "/webservice?id=PLMKeyService";
 
    
    return (isError);
  }

  /**
   * Printusage
   * Simple help for usage
   * 
   */
  void PrintUsage()
  {
    Console.WriteLine("Usage:");
    Console.WriteLine("TestCAAPLMKey -h | -k adminKey -u rootURI [-n userName] [-p userPassword] [-a productName] [-c company] [-m keyValidityInMn]");
    Console.WriteLine("\toptions :");
    Console.WriteLine("\t\t-k adminKey : the administration key which allows to access the PLMKeyService web service");
    Console.WriteLine("\t\t-u rootURI  : the URI of deployed application. Format :http://host:port/application");
    Console.WriteLine("\t\t-n userName : user associated to the key to be generated (default=\"fooUser\")");
    Console.WriteLine("\t\t-p userPwd  : the password of the user above (default=\"XXXXXX\")");
    Console.WriteLine("\t\t-a prdName  : the product Name(default=\"CATIA V5\")");
    Console.WriteLine("\t\t-c company  : the company will supplies the product(default=\"DASSAULT SYSTEMES\")");
    Console.WriteLine("\t\t-m minutes  : the validity of the key in minutes(default=\"1\") ");
    
    Console.WriteLine("the web service generates a key value, for a credential set definition named \"CAATST\" (credentials: [user,pwd],[product,companyname],[key validity in minutes]");
  }

  /**
   * CallService
   * Parms were already read. Now call the service proxy classes
   * 
   */
  void CallService()
  {

    string simpleGenKey = null;
    string structGenKey = null;

    // Instantiate the service, and create the service url 
    Console.WriteLine();
    Console.WriteLine("   CLIENT>> creates the client proxy : use input url.");
    Console.WriteLine();

    PLMKeyManagementClient genKeySvc = new PLMKeyManagementClient("IPLMKeyManagement", _url);

    try
    {

      Console.WriteLine("   CLIENT>> call the first web method of the PLMKey service.");
      Console.WriteLine("   CLIENT>> ======================================================.\n");
      string contextURI = "CAATEST:" + _companyName + ":" + _productName;
      string validityURI = "CAATEST:" + _validityInMn;
      Console.WriteLine("   CLIENT>> input data:");
      Console.WriteLine("\tadminKey   =\"" + _adminKey + "\"");
      Console.WriteLine("\tuserName   =\"" + _userName + "\"");
      Console.WriteLine("\tuserPwd    =\"" + _pwdName + "\"");
      Console.WriteLine("\tcontextURI =\"" + contextURI + "\"");
      Console.WriteLine("\tvalidityURI=\"" + validityURI + "\"");
      Console.WriteLine("   CLIENT>> call the generatePLMKeyFromSimpleContext.\n");

      simpleGenKey = genKeySvc.generatePLMKeyFromSimpleContext(_adminKey,_userName, _pwdName, contextURI, validityURI);

      Console.WriteLine("   CLIENT>> key generated from simple context =\"{0}\"\n\n", simpleGenKey);


    }
    catch (Exception e)
    {
      Console.WriteLine(">>>PLMKey SERVICE EXCEPTION (SIMPLE CONTEXT METHOD) <<<\n" + e);
    }
    try
    {
      Console.WriteLine("   CLIENT>> Test the 2nd web method of the PLMKey service. ");
      Console.WriteLine("   CLIENT>> ======================================================.\n");

      // create the credentialset object and fill it with the right credential values:
      // (given that the credentials name and type are know by the client)
      // authentication : <user> <password>
      // applicative    : <company> <product>
      // validity       : <validityMinutes>
      CredentialSet csInstance = new CredentialSet();
      csInstance.id = "CAATEST";

      // a) the authentication credentials.

      credential userCred = new credential();
      userCred.name = "user";
      userCred.Value = _userName;

      credential pwdCred = new credential();
      pwdCred.name = "password";
      pwdCred.Value = _pwdName;
      pwdCred.ispassword = true;
      // add the credentials to the credential set authentication part.
      csInstance.authentication = new credential[2];
      csInstance.authentication[0] = userCred;
      csInstance.authentication[1] = pwdCred;


      //b) the applicative credentials
      credential companyCred = new credential();
      companyCred.name = "company";
      companyCred.Value = _companyName;

      credential productCred = new credential();
      productCred.name = "product";
      productCred.Value = _productName;
      csInstance.applicative = new credential[2];
      csInstance.applicative[0] = companyCred;
      csInstance.applicative[1] = productCred;

      //c) the validity credentials 

      credential validCred = new credential();
      validCred.name = "validityMinutes";
      validCred.Value = _validityInMn.ToString();
      csInstance.validity = new credential[1];
      csInstance.validity[0] = validCred;

      structGenKey = genKeySvc.generatePLMKeyFromStructuredContext(_adminKey,csInstance);
      Console.WriteLine("   CLIENT>> key generated from structured context =\"{0}\"\n\n", structGenKey);
    }

    catch (Exception e)
    {
      Console.WriteLine(">>>PLMKey SERVICE EXCEPTION (STRUCTURED CONTEXT METHOD) <<<\n" + e);
    }

    string keysToRevoke = null;
    if (simpleGenKey != null)
      keysToRevoke = simpleGenKey;
    if (structGenKey != null)
      if (keysToRevoke == null)
        keysToRevoke = structGenKey;
      else
      {
        keysToRevoke += " ";
        keysToRevoke += structGenKey;
      }
    try
    {
      Console.WriteLine("   CLIENT>> Test revocation web method of the PLMKey service. ");
      Console.WriteLine("   CLIENT>> ======================================================.\n");
      genKeySvc.revokePLMKeys(_adminKey,keysToRevoke);
      Console.WriteLine("   CLIENT>> revocation of keys =\"{0}\" is done", keysToRevoke);
    }
    catch (Exception e)
    {
      Console.WriteLine(">>>PLMKey SERVICE EXCEPTION (KEY REVOCATION METHOD) <<<\n" + e);
    }
  }
}
    

 /* end module */


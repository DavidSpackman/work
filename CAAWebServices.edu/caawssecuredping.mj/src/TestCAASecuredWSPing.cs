/** 
 * This program may be used, executed, copied, modified and distributed
 * without royalty for the purpose of developing, using, marketing, or distributing.
 * 
 **/


using System;
using System.Threading;
using www._3ds.com.wserror;

public partial class TestCAASecuredWSPing
{


  string msg = "HELLO";
  string url="";


  /**
   * Main
   * main program entry point
   * 
   * @param args - string[]
   */
  static void Main(string[] args)
  {
    TestCAASecuredWSPing ws = new TestCAASecuredWSPing();
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
              case 'm':
              case 'M':
                msg = args[++argc];
                break;
              case 'u':
              case 'U':
                url = args[++argc];
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
    Console.WriteLine("  TestCAASecuredWSPing -u [service_url]  -m [testMessage]");
    Console.WriteLine("Default values:");
    Console.WriteLine("  testMessage= \"hello\"");

  }

  /**
   * CallService
   * Parms were already read. Now call the service proxy classes
   * 
   */
  void CallService()
  {

    // Instantiate the service, and create the service url 
    PingServiceClient pingsv;
    if (url.Length == 0){
      // case : when the configuration is generated from the uri ( web service deployed in WCF and secured);
      Console.WriteLine("CLIENT>> creates the client proxy : use Ping endpoint from config file.");
      Console.WriteLine();
      pingsv = new PingServiceClient();
    }
    else{
      // Else ( Web service deployed on was, or proxy generated from a static wsdl file).
      Console.WriteLine("CLIENT>> creates the client proxy : use input url.");
      Console.WriteLine();
        // the first parameter must equals the "name" attributevalue of the <endpoint> tag in the config file.
      // here, it's the name you find in the "TestCAASecuredWSPing.exe.was.config" file.
        // Adapt the value, according to the name found in the associated config file.
        // the second parameter is the url...
        
      pingsv = new PingServiceClient("IPingServiceBinding_IPingService", url);
      
    }

    

    try
    {
      Console.WriteLine("CLIENT>> call the ping method.\n");
      System.Nullable<int> count = pingsv.ping(msg);

      string[] words = msg.Split(null);
      int wc = words.Length;
      if (count != wc){
        Console.WriteLine("CLIENT>> An error has occurred, count={0} and should be {1}", count, wc);
      }
      else
      {
        Console.WriteLine("CLIENT>>   input ping request = \"{0}\" ",msg);
        Console.WriteLine("CLIENT>> words count response =  {0} ", count);
        Console.WriteLine("CLIENT>> ===> OK");
      }
    }

    catch (Exception e)
    {
      Console.WriteLine(">>>PING SERVICE EXCEPTION<<<\n" + e);
    }


  }

}
    

 /* end module */


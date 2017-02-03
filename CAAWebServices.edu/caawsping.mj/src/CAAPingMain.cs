using System;
using System.Text;
using System.Threading;
using System.ServiceModel;
using com.matrixone.jsystem.ws.generated.ping;

namespace com.dassault_systemes.ping.client {
    class PingMain {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args) {
            if (args.Length == 0) {
                Console.Out.WriteLine("\nUsage: caawsping <appserver_root_uri>");
                Environment.Exit(0);
            }
            try {
                // A few proverbs for tests.
                string[] testData ={"Calling the PING web service in a CAA context",
                                    "He that will steal a penny will steal a pound.",
                                    "When the cat is away, the mice will play",
                                    "The early bird catches the worm."};
                int[] wordCounts ={ 9, 10, 9, 6 };

                // Compute the service address based on the supplied application
                // server root URI 
                string serviceAddress = args[0] + "/webservice?id=ping";

                // Create an instance of ping proxy. One needs to specify:
                // - the name of the binding (it must match the binding name defined
                // in App.config).
                // - the address of the service
                PingServiceClient pingProxy = new PingServiceClient("IPingServiceBinding", serviceAddress);

                // Call the Ping web service four times. 
                // The Web Service output is the word count of the input string...
                Console.WriteLine("Ping {0}", serviceAddress);


                for (int i = 0; i < 4; i++) {
                    System.Nullable<int> count = pingProxy.ping(testData[i]);
                    if (count != wordCounts[i]) {
                        Console.WriteLine("An error has occurred, count={0} and should be {1}", count, wordCounts[i]);
                    } else {
                        Console.Write(" input={0} ---> words count={1}", testData[i], count);
                        Console.WriteLine(" OK");
                    }
                }
            } catch (FaultException<ErrorType> error) {
                // Handle application level errors.
                Console.WriteLine("Error {0} has occurred: {1}", error.Detail.id, error.Detail.message);
                Environment.Exit(1);
            } catch (Exception e) {
                // Handle other errors.
                Console.WriteLine(e.ToString());
                Environment.Exit(1);
            }
        }
    }
}

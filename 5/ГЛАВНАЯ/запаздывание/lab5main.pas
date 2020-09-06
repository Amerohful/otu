unit lab5main;
//рисунок 3
interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, TeEngine, Series, ExtCtrls, TeeProcs, Chart, ComCtrls;

type
  TForm1 = class(TForm)
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    Chart1: TChart;
    Graphic2: TFastLineSeries;
    Graphic3: TFastLineSeries;
    Series1: TFastLineSeries;
    procedure FormCreate(Sender: TObject);
  private     { Private declarations }
  public      { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}
//////////////////////////////////////////////////
///  ПРОЦЕДУРА МОДЕЛИРОВАНИЯ ПЕРЕХОДНЫХ ПРОЦЕССОВ
//////////////////////////////////////////////////
procedure TForm1.FormCreate(Sender: TObject);
////////////////////////////////////////////
   Const T=0.2; k=2;
         g=1.0;

 Var   lin, dt, y,y1,y2,y3,x,z1,z2,z3,std,k1,k2,k3,k4,tau,ns:real;
       i,j:integer;
       mas:array[1..50000] of real;

  begin
     lin:=10;   dt:=0.00001;
     z1:=0; z2:=0;z3:=0;
     std:=0.0;

     tau:=0.261941;{,0.125658; 0.15;} //важно для запаздывания
     ns:=tau/dt;               //важно для запаздывания
     i:=0;j:=0;                //важно для запаздывания

      while std<lin do
        begin
          std:=std+dt;
  x:=g-y;
  y1:=z1;   //3 звено
  k1:=(k/t*x-y1/t)*dt;
  k2:=(k/t*x-(y1+k/2)/t)*dt;
  k3:=(k/t*x-(y1+k/2)/t)*dt;
  k4:=(k/t*x-(y1+k)/t)*dt;
  z1:=z1+(k1+2*k2+2*k3+k4)/6;
  y2:=z2;
  z2:=z2+y1*dt;
          //Запаздывание
          i:=i+1;
          if(i>ns) then
            begin
               if(j>=ns) then j:=0;
               j:=j+1;
               y:=mas[j];
               mas[j]:=y2;
             end
           else
               begin
                   mas[i]:=y2;
                   y:=0;
                end;
  Series1.AddXY(std,y2);  //график без запаздывания
  Graphic2.AddXY(std,g);  //входное воздействие
  Graphic3.AddXY(std,y);  //график с учетом запаздывания
          end;
//КОНЕЦ МОДЕЛИРОВАНИЯ
////////////////////////////////////////////

end;

end.


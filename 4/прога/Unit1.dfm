object Form1: TForm1
  Left = 580
  Top = 171
  Width = 1020
  Height = 665
  Caption = 'Form1'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object Chart1: TChart
    Left = 0
    Top = 0
    Width = 1004
    Height = 626
    BackWall.Brush.Color = clWhite
    BackWall.Brush.Style = bsClear
    Title.Text.Strings = (
      #1056#1077#1079#1091#1083#1100#1090#1072#1090#1099' '#1084#1086#1076#1077#1083#1080#1088#1086#1074#1072#1085#1080#1103' 1 '#1083#1072#1073' '#1088#1072#1073)
    BottomAxis.ExactDateTime = False
    BottomAxis.Increment = 0.001000000000000000
    LeftAxis.ExactDateTime = False
    LeftAxis.Increment = 0.001000000000000000
    Legend.Alignment = laBottom
    View3D = False
    Align = alClient
    TabOrder = 0
    object StaticText1: TStaticText
      Left = 88
      Top = 16
      Width = 58
      Height = 17
      Caption = 'StaticText1'
      TabOrder = 0
    end
    object Series1: TFastLineSeries
      Marks.ArrowLength = 8
      Marks.Visible = False
      SeriesColor = clRed
      Title = 'h(t)'
      LinePen.Color = clRed
      XValues.DateTime = False
      XValues.Name = 'X'
      XValues.Multiplier = 1.000000000000000000
      XValues.Order = loAscending
      YValues.DateTime = False
      YValues.Name = 'Y'
      YValues.Multiplier = 1.000000000000000000
      YValues.Order = loNone
    end
    object Series2: TFastLineSeries
      Marks.ArrowLength = 8
      Marks.Visible = False
      SeriesColor = clGreen
      Title = 'w(t)'
      LinePen.Color = clGreen
      XValues.DateTime = False
      XValues.Name = 'X'
      XValues.Multiplier = 1.000000000000000000
      XValues.Order = loAscending
      YValues.DateTime = False
      YValues.Name = 'Y'
      YValues.Multiplier = 1.000000000000000000
      YValues.Order = loNone
    end
    object Series3: TFastLineSeries
      Marks.ArrowLength = 8
      Marks.Visible = False
      SeriesColor = clBlue
      Title = 'g(t)'
      LinePen.Color = clBlue
      XValues.DateTime = False
      XValues.Name = 'X'
      XValues.Multiplier = 1.000000000000000000
      XValues.Order = loAscending
      YValues.DateTime = False
      YValues.Name = 'Y'
      YValues.Multiplier = 1.000000000000000000
      YValues.Order = loNone
    end
    object Series4: TFastLineSeries
      Marks.ArrowLength = 8
      Marks.Visible = False
      SeriesColor = 8388863
      Title = 'h(t) '#1072#1085#1072#1083#1080#1090#1080#1095
      LinePen.Color = 8388863
      XValues.DateTime = False
      XValues.Name = 'X'
      XValues.Multiplier = 1.000000000000000000
      XValues.Order = loAscending
      YValues.DateTime = False
      YValues.Name = 'Y'
      YValues.Multiplier = 1.000000000000000000
      YValues.Order = loNone
    end
    object Series5: TFastLineSeries
      Marks.ArrowLength = 8
      Marks.Visible = False
      SeriesColor = 4194432
      Title = 'w(t) '#1072#1085#1072#1083#1080#1090#1080#1095
      LinePen.Color = 4194432
      XValues.DateTime = False
      XValues.Name = 'X'
      XValues.Multiplier = 1.000000000000000000
      XValues.Order = loAscending
      YValues.DateTime = False
      YValues.Name = 'Y'
      YValues.Multiplier = 1.000000000000000000
      YValues.Order = loNone
    end
  end
end

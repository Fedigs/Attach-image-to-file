import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, Validators } from '@angular/forms';
import { dataModel } from '../../Models/dataModel';
import { TaskServiceService } from '../../Services/task-service.service';

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent implements OnInit {

  img: File;
  formData: FormData;
  submitted: boolean = false;
  showSuccessMsg: boolean = false;
  showErrorMsg: boolean = false;
  msg:string;
  constructor(
    public fb: FormBuilder,
    private taskServiceService: TaskServiceService) { }

  taskForm = this.fb.group({
    img: [
      '',
      [
        Validators.required,
      ],
    ],
    x: [
      '',
      [
        Validators.required,
        Validators.pattern('^[0-9]+$'),
      ],
    ],
    y: [
      '',
      [
        Validators.required,
        Validators.pattern('^[0-9]+$'),
      ],
    ],

  });

  get myForm() {
    return this.taskForm.controls;
  }

  ngOnInit(): void {

  }


  upload(event) {
    this.formData = new FormData();
    this.img = event.target.files.item(0);
    this.formData.append('img', this.img);
  }

  submit() {
    this.submitted = true;
   if (this.taskForm.valid) {

      let data: dataModel = {
        'x': this.taskForm.value.x,
        'y': this.taskForm.value.y,
        'img': this.formData
      };

      this.taskServiceService.attachImageToPDF(data).subscribe({
        next: (res) => {
          console.log(res)
          this.msg = res;
          this.showSuccessMsg = true;
          setTimeout(()=>{
            this.showSuccessMsg = false;
          },2000);
        },
        error: (error) => {
          console.log(error.message)

          this.showErrorMsg = true;
          setTimeout(()=>{
            this.showErrorMsg = false;
          },2000);
        }

      });



    }

  }
}

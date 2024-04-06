import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovedAdminComponent } from './approved-admin.component';

describe('ApprovedAdminComponent', () => {
  let component: ApprovedAdminComponent;
  let fixture: ComponentFixture<ApprovedAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApprovedAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ApprovedAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
